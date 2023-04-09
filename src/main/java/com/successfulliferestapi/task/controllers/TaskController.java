package com.successfulliferestapi.Task.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Task.constants.TaskMessages;
import com.successfulliferestapi.Task.models.dto.AddTaskDTO;
import com.successfulliferestapi.Task.services.TaskService;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    //CREATE New Task
    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody AddTaskDTO addTaskDTO,
                                     BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.status(201).body(taskService.addTask(addTaskDTO, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Task By ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id,Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getById(id,user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Tasks by TARGET ID
    @GetMapping("/targets/{targetId}")
    public ResponseEntity<?> getTasksByTargetId(
            @PathVariable Long targetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by(
                    Sort.Order.desc("priority"),
                    Sort.Order.asc("dueDate").nullsLast()
            ));
            return ResponseEntity.ok(taskService.getTasksByTargetId(user.getId(), targetId, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
