package com.successfulliferestapi.Target.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Target.exceptions.TargetException;
import com.successfulliferestapi.Target.models.dto.AddTargetDTO;
import com.successfulliferestapi.Target.models.dto.UpdateTargetDescriptionDTO;
import com.successfulliferestapi.Target.models.dto.UpdateTargetTitleDTO;
import com.successfulliferestapi.Target.services.TargetService;
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
@RequestMapping("/api/v1/targets")
@RequiredArgsConstructor
public class TargetController {
    private final TargetService targetService;

    //CREATE New Target
    @PostMapping
    public ResponseEntity<?> createTarget(@Valid @RequestBody AddTargetDTO addTargetDTO,
                                          BindingResult result,
                                          Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(201).body(targetService.add(user, addTargetDTO));
        } catch (TargetException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //UPDATE Target Title
    @PatchMapping("/edit/{targetId}/title")
    public ResponseEntity<?> updateTargetTitle(@Valid @RequestBody UpdateTargetTitleDTO request,
                                        BindingResult result, @PathVariable Long targetId,
                                        Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            System.out.println(request.getTitle());
            return ResponseEntity.ok(targetService.changeTitle(user.getId(),targetId,request));
        } catch (TargetException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    //UPDATE Target Description
    @PatchMapping("/edit/{targetId}/description")
    public ResponseEntity<?> updateTargetDescription(@Valid @RequestBody UpdateTargetDescriptionDTO updateTargetDescriptionDTO,
                                        BindingResult result,
                                        @PathVariable Long targetId,
                                        Authentication authentication) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(targetService.changeDescription(user.getId(),targetId, updateTargetDescriptionDTO));
        } catch (TargetException exception) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
        }
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<?> deleteTarget(@PathVariable Long targetId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(targetService.deleteTarget(targetId,user.getId()));
        } catch (TargetException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Target By ID
    @GetMapping("/{targetId}")
    public ResponseEntity<?> getTargetById(@PathVariable Long targetId,
                                           Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(targetService.getById(targetId, user.getId()));
        } catch (TargetException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET All Goal Targets
    @GetMapping("/goals/{goalId}")
    public ResponseEntity<?> getGoalTargets(@PathVariable Long goalId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "48") int size,
                                            Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            return ResponseEntity.ok(targetService.getAllByGoalId(goalId, user.getId(),pageable));
        } catch (TargetException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
