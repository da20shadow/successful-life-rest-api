package com.successfulliferestapi.Goal.controllers;

import com.successfulliferestapi.Goal.exceptions.GoalException;
import com.successfulliferestapi.Goal.models.dto.AddGoalDTO;
import com.successfulliferestapi.Goal.models.dto.UpdateGoalDeadlineDTO;
import com.successfulliferestapi.Goal.models.dto.UpdateGoalDescriptionDTO;
import com.successfulliferestapi.Goal.models.dto.UpdateGoalTitleDTO;
import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import com.successfulliferestapi.Goal.services.GoalService;
import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<?> createGoal(@Valid @RequestBody AddGoalDTO addGoalDTO,
                                     BindingResult result, Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(201).body(goalService.add(user,addGoalDTO));
        } catch (GoalException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PostMapping("/recover/{goalId}")
    public ResponseEntity<?> recoverGoal(@PathVariable Long goalId, Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(201).body(goalService.recoverDeletedGoal(goalId,user.getId()));
        } catch (GoalException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PatchMapping("/edit/{goalId}/title")
    public ResponseEntity<?> updateGoalTitle(@Valid @RequestBody UpdateGoalTitleDTO updateGoalTitleDTO,
                                        BindingResult result, @PathVariable Long goalId,
                                        Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.changeTitle(user.getId(),goalId,updateGoalTitleDTO));
        } catch (GoalException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PatchMapping("/edit/{goalId}/description")
    public ResponseEntity<?> updateGoalDescription(@Valid @RequestBody UpdateGoalDescriptionDTO updateGoalDescriptionDTO,
                                        BindingResult result,
                                        @PathVariable Long goalId,
                                        Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.changeDescription(user.getId(),goalId, updateGoalDescriptionDTO));
        } catch (GoalException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @PatchMapping("/edit/{goalId}/deadline")
    public ResponseEntity<?> updateGoalDeadline(@Valid @RequestBody UpdateGoalDeadlineDTO updateGoalDeadlineDTO,
                                        BindingResult result,
                                        @PathVariable Long goalId,
                                        Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.changeDeadline(user.getId(),goalId, updateGoalDeadlineDTO));
        } catch (GoalException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    //Soft DELETE Goal
    @DeleteMapping("/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.deleteGoal(goalId,user.getId()));
        } catch (GoalException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Permanent DELETE Goal
    @DeleteMapping("/permanent/{goalId}")
    public ResponseEntity<?> permanentDeleteGoal(@PathVariable Long goalId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.permanentDeleteGoal(goalId,user.getId()));
        } catch (GoalException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGoals(@RequestParam(defaultValue = "") String category,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "24") int size,
                                         Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            GoalCategory goalCategory = category.equals("") ? null : GoalCategory.valueOf(category);
            if (goalCategory != null) {
                return ResponseEntity.ok(goalService.getAllByCategory(user.getId(), goalCategory, pageable));
            }
            return ResponseEntity.ok(goalService.getAll(user.getId(), pageable));
        } catch (GoalException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getAllDeletedGoals(@RequestParam(defaultValue = "") String category,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "24") int size,
                                         Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            GoalCategory goalCategory = category.equals("") ? null : GoalCategory.valueOf(category);
            if (goalCategory != null) {
                return ResponseEntity.ok(goalService.getAllDeletedByCategory(user.getId(), goalCategory, pageable));
            }
            return ResponseEntity.ok(goalService.getAllDeleted(user.getId(), pageable));
        } catch (GoalException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<?> getGoalById(@PathVariable Long goalId,
                                         Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(goalService.getById(goalId,user.getId()));
        } catch (GoalException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
