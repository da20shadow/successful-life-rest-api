package com.successfulliferestapi.Task.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Task.constants.TaskMessages;
import com.successfulliferestapi.Task.exceptions.TaskException;
import com.successfulliferestapi.Task.models.dto.AddTaskDTO;
import com.successfulliferestapi.Task.models.dto.EditTaskDTO;
import com.successfulliferestapi.Task.services.TaskService;
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
import java.util.Objects;
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
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
    //UPDATE Task
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @Valid @RequestBody EditTaskDTO editTaskDTO,
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
            return ResponseEntity.ok(taskService.updateTask(id, editTaskDTO, user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(TaskMessages.Error.UPDATE));
        }
    }

    //DELETE task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.deleteTask(id, user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(TaskMessages.Error.UPDATE));
        }
    }

    //GET Task By ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id, Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getById(id, user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET today tasks
    @GetMapping("/today")
    public ResponseEntity<?> getTodayTasks(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getTodayTasks(user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET tasks by Date
    @GetMapping("/date")
    public ResponseEntity<?> getTasksByDate(@RequestParam(defaultValue = "") String date,
                                            Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
//            String decodedDate = URLDecoder.decode(date, "UTF-8"); // Decode the date string
            return ResponseEntity.ok(taskService.getTasksByDate(user.getId(),date));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Week Tasks
    @GetMapping("/week")
    public ResponseEntity<?> getWeekTasks(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            if (from.equals("")) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid From Date!"));
            }
            if (to.equals("")) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid To Date!"));
            }
            return ResponseEntity.ok(taskService.getWeekTasks(user.getId(),from,to));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Month Tasks
    @GetMapping("/month")
    public ResponseEntity<?> getMonthTasks(
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            if (year == 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Year!"));
            }
            if (month == 0) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Month!"));
            }
            return ResponseEntity.ok(taskService.getAllTasksForMonth(user.getId(),year,month));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Important Tasks
    @GetMapping("/important")
    public ResponseEntity<?> getImportantTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();

            Pageable pageable = PageRequest.of(page,
                    size, Sort.by("important").descending()
                            .and(Sort.by("createdAt").ascending()));
            return ResponseEntity.ok(taskService.getImportantTasks(user.getId(), pageable));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //GET Urgent Tasks
    @GetMapping("/urgent")
    public ResponseEntity<?> getUrgentTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, size, Sort.by("urgent").descending());
            return ResponseEntity.ok(taskService.getUrgentTasks(user.getId(), pageable));
        } catch (TaskException e) {
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
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(taskService.getAllTasks(user.getId()));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

//    @GetMapping
//    public ResponseEntity<?> getAllTasks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "25") int size,
//            Authentication authentication) {
//
//        try {
//            User user = (User) authentication.getPrincipal();
//            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
//            return ResponseEntity.ok(taskService.getAllTasks(user.getId(), pageable));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
//        }
//    }

}
