package com.successfulliferestapi.Task.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Task.exceptions.TaskException;
import com.successfulliferestapi.Task.models.dto.AddChecklistItemDTO;
import com.successfulliferestapi.Task.models.dto.EditChecklistItemDTO;
import com.successfulliferestapi.Task.services.ChecklistService;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/checklists")
@RequiredArgsConstructor
public class ChecklistItemController {

    private final ChecklistService checklistService;

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<?> addChecklistItem(@PathVariable Long taskId,
                                              @RequestBody @Valid AddChecklistItemDTO checklistItem,
                                              Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.add(taskId, user, checklistItem));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<?> markAllAsCompleted(@PathVariable Long taskId,
                                                Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.markAllAsCompleted(taskId, user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> editChecklistItem(@PathVariable Long itemId,
                                               @Valid @RequestBody EditChecklistItemDTO item, BindingResult result,
                                               Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.editItem(itemId, user.getId(), item));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteChecklistItem(@PathVariable Long itemId,
                                                 Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().body(checklistService.deleteItem(itemId, user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<?> getTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/tasks/{taskId}/completed")
    public ResponseEntity<?> getCompletedTaskChecklistItems(@PathVariable Long taskId, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(checklistService.getAllByTaskId(taskId,user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
