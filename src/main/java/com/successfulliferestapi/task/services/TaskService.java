package com.successfulliferestapi.Task.services;

import com.successfulliferestapi.Target.constants.TargetMessages;
import com.successfulliferestapi.Target.exceptions.TargetException;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Target.repositories.TargetRepository;
import com.successfulliferestapi.Task.constants.TaskMessages;
import com.successfulliferestapi.Task.exceptions.TaskException;
import com.successfulliferestapi.Task.models.dto.AddTaskDTO;
import com.successfulliferestapi.Task.models.dto.AddTaskSuccessResponseDTO;
import com.successfulliferestapi.Task.models.dto.TaskDTO;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.models.enums.TaskStatus;
import com.successfulliferestapi.Task.repositories.TaskRepository;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TargetRepository targetRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AddTaskSuccessResponseDTO addTask(AddTaskDTO addTaskDTO, User user) {

        Task task = modelMapper.map(addTaskDTO, Task.class);

        if (addTaskDTO.getTargetId() != null) {
            Optional<Task> existTask = taskRepository.findByTitleAndTargetIdAndUserId(addTaskDTO.getTitle(), addTaskDTO.getTargetId(), user.getId());
            existTask.ifPresent(t -> {
                throw new TaskException(TaskMessages.Error.DUPLICATE_TITLE);
            });

            Optional<Target> optionalTarget = targetRepository.findByIdAndUserId(addTaskDTO.getTargetId(), user.getId());
            Target target = optionalTarget.orElseThrow(() -> new TargetException(TargetMessages.Error.NOT_FOUND));
            task.setTarget(target);

        }

        if (addTaskDTO.getStatus() == null) {
            task.setStatus(TaskStatus.TO_DO);
        } else {
            try {
                TaskStatus status = TaskStatus.valueOf(addTaskDTO.getStatus());
                task.setStatus(status);
            } catch (IllegalArgumentException e) {
                throw new TaskException("Invalid status");
            }
        }

        if (addTaskDTO.getPriority() == null) {
            task.setPriority(TaskPriority.NO_PRIORITY);
        } else {
            try {
                TaskPriority priority = TaskPriority.valueOf(addTaskDTO.getPriority());
                task.setPriority(priority);
            } catch (IllegalArgumentException e) {
                throw new TaskException("Invalid priority");
            }
        }

        task.setChecklist(new HashSet<>());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        Task addedTask = taskRepository.save(task);
        TaskDTO taskDTO = modelMapper.map(addedTask, TaskDTO.class);
        if (addTaskDTO.getTargetId() != null) {
            taskDTO.setTargetId(addTaskDTO.getTargetId());
        }
        return new AddTaskSuccessResponseDTO(TaskMessages.Success.ADD,taskDTO);
    }

    @Transactional
    public Page<TaskDTO> getTasksByTargetId(Long userId, Long targetId, Pageable pageable) {
        return taskRepository.findByUserIdAndTargetId(userId, targetId, pageable)
                .map(t -> {
                    TaskDTO taskDTO = modelMapper.map(t, TaskDTO.class);
                    if (t.getTarget().getId() != null) {
                        taskDTO.setTargetId(t.getTarget().getId());
                    }
                    return taskDTO;
                });
    }

    public TaskDTO getById(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);
        if (optionalTask.isEmpty()) {
            throw new TaskException(TaskMessages.Error.NOT_FOUND);
        }
        return modelMapper.map(optionalTask.get(), TaskDTO.class);
    }
}
