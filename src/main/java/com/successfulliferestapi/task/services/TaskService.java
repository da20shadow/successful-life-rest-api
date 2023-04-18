package com.successfulliferestapi.Task.services;

import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.Target.constants.TargetMessages;
import com.successfulliferestapi.Target.exceptions.TargetException;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Target.repositories.TargetRepository;
import com.successfulliferestapi.Task.constants.TaskMessages;
import com.successfulliferestapi.Task.events.TaskUpdatedEvent;
import com.successfulliferestapi.Task.exceptions.TaskException;
import com.successfulliferestapi.Task.models.dto.*;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.models.enums.TaskStatus;
import com.successfulliferestapi.Task.repositories.TaskRepository;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());
    private final TaskRepository taskRepository;
    private final TargetRepository targetRepository;
    private final ModelMapper modelMapper;
//    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public AddTaskSuccessResponseDTO addTask(AddTaskDTO addTaskDTO, User user) {

        Task task = modelMapper.map(addTaskDTO, Task.class);

        if (addTaskDTO.getTargetId() != null) {
            Optional<Task> existTask = taskRepository.findByTitleAndTargetIdAndUser_Id(addTaskDTO.getTitle(), addTaskDTO.getTargetId(), user.getId());
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
        return new AddTaskSuccessResponseDTO(TaskMessages.Success.ADDED, taskDTO);
    }

    //UPDATE TASK
    public EditTaskSuccessResponseDTO updateTask(Long taskId, EditTaskDTO editTaskDTO, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);

        if (optionalTask.isEmpty()) {
            throw new TaskException(TaskMessages.Error.NOT_FOUND);
        }

        Task task = optionalTask.get();

        if (editTaskDTO.getTitle() != null) {
            task.setTitle(editTaskDTO.getTitle());
        }
        if (editTaskDTO.getDescription() != null) {
            task.setDescription(editTaskDTO.getDescription());
        }
        if (editTaskDTO.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(editTaskDTO.getStatus()));
        }
        if (editTaskDTO.getPriority() != null) {
            task.setPriority(TaskPriority.valueOf(editTaskDTO.getPriority()));
        }

        if (Objects.equals(editTaskDTO.isImportant(), Boolean.TRUE)) {
            task.setImportant(true);
        } else if (Objects.equals(editTaskDTO.isImportant(), Boolean.FALSE)) {
            task.setImportant(false);
        }

        if (Objects.equals(editTaskDTO.isUrgent(), Boolean.TRUE)) {
            task.setUrgent(true);
        } else if (Objects.equals(editTaskDTO.isUrgent(), Boolean.FALSE)) {
            task.setUrgent(false);
        }

        if (editTaskDTO.getStartDate() != null) {
            if (editTaskDTO.getStartDate().equals("clear")) {
                task.setStartDate(null);
            } else {
                LocalDateTime startDate = LocalDateTime.parse(editTaskDTO.getStartDate());
                task.setStartDate(startDate);
            }
        }

        if (editTaskDTO.getDueDate() != null) {
            if (editTaskDTO.getDueDate().equals("clear")) {
                task.setDueDate(null);
            } else {
                LocalDateTime dueDate = LocalDateTime.parse(editTaskDTO.getDueDate());
                task.setDueDate(dueDate);
            }
        }

        task = taskRepository.save(task);
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);

        // Publish the TaskUpdatedEvent event
//        eventPublisher.publishEvent(new TaskUpdatedEvent(task));

        return new EditTaskSuccessResponseDTO(TaskMessages.Success.UPDATED, taskDTO);
    }

    public SuccessResponseDTO deleteTask(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);
        if (optionalTask.isEmpty()) {
            throw new TaskException(TaskMessages.Error.NOT_FOUND);
        }
        taskRepository.delete(optionalTask.get());
        return new SuccessResponseDTO(TaskMessages.Success.DELETED);
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

    //GET TASK By ID
    public TaskDTO getById(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, userId);
        if (optionalTask.isEmpty()) {
            throw new TaskException(TaskMessages.Error.NOT_FOUND);
        }
        return modelMapper.map(optionalTask.get(), TaskDTO.class);
    }

    //Get Tasks By Date
    public List<TaskDTO> getTasksByDate(Long userId, String stringDate) {
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE_TIME);
        List<Task> todayTasks = taskRepository.findNotCompletedTasksByDate(userId, date);
        return todayTasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());
    }

    //GET Today Tasks
    public List<TaskDTO> getTodayTasks(Long userId) {
        LocalDate today = LocalDate.now();
        List<Task> todayTasks = taskRepository.findNotCompletedTodayTasks(userId, today);
        return todayTasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());
    }

    //GET Week Tasks
    public List<TaskDTO> getWeekTasks(Long userId, String fromDate, String toDate) {
        LocalDateTime from = LocalDateTime.parse(fromDate);
        LocalDateTime to = LocalDateTime.parse(toDate);
        List<Task> tasks = taskRepository.findAllByUserIdAndWeek(userId, from, to, from, to);
        return tasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());
    }

    //GET Month Tasks
    public List<TaskDTO> getAllTasksForMonth(Long userId, int year, int month) {
        return taskRepository.findAllByUserIdAndMonth(userId, year, month)
                .stream()
                .map(t -> modelMapper.map(t, TaskDTO.class))
                .collect(Collectors.toList());
    }

    //GET Urgent Tasks
    public Page<TaskDTO> getUrgentTasks(Long userId, Pageable pageable) {
        return taskRepository.findByUserIdAndUrgentTrue(userId, pageable)
                .map(t -> modelMapper.map(t, TaskDTO.class));
    }

    //GET Important Tasks
    public Map<String, Page<TaskDTO>> getImportantTasks(Long userId, Pageable pageable) {
        LocalDate today = LocalDate.now();

        Page<Task> todayTasksDB = taskRepository.findTodayImportantTasks(userId, today, pageable);
        Page<Task> overdueTasksDB = taskRepository.findAllOverdueImportantTasks(userId, today, pageable);
        Page<Task> nextTasksDB = taskRepository.findAllNextImportantTasks(userId, today, pageable);
        Page<Task> unscheduledTasksDB = taskRepository.findAllUnscheduledImportantTasks(userId, pageable);

        Page<TaskDTO> todayTasks = todayTasksDB.map(t -> modelMapper.map(t, TaskDTO.class));
        Page<TaskDTO> overdueTasks = overdueTasksDB.map(t -> modelMapper.map(t, TaskDTO.class));
        Page<TaskDTO> nextTasks = nextTasksDB.map(t -> modelMapper.map(t, TaskDTO.class));
        Page<TaskDTO> unscheduledTasks = unscheduledTasksDB.map(t -> modelMapper.map(t, TaskDTO.class));

        Map<String, Page<TaskDTO>> result = new HashMap<>();
        result.put("todayTasks", todayTasks);
        result.put("overdueTasks", overdueTasks);
        result.put("nextTasks", nextTasks);
        result.put("unscheduledTasks", unscheduledTasks);
        return result;
    }

    //GET All Tasks
    public Map<String, List<TaskDTO>> getAllTasks(Long userId) {
        LocalDate todayDate = LocalDate.now();

        List<Task> overdueTasks = taskRepository.findAllOverdueTasks(userId, todayDate);
        List<Task> nextTasks = taskRepository.findAllNextTasks(userId, todayDate);
        List<Task> unscheduledTasks = taskRepository.findAllUnscheduledTasks(userId);

        Map<String, List<TaskDTO>> response = new LinkedHashMap<>();

        List<TaskDTO> overdueTasksDTO = overdueTasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());
        List<TaskDTO> nextTasksDTO = nextTasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());
        List<TaskDTO> unscheduledTasksDTO = unscheduledTasks.stream().map(t -> modelMapper.map(t, TaskDTO.class)).collect(Collectors.toList());

        response.put("overdueTasks", overdueTasksDTO);
        response.put("nextTasks", nextTasksDTO);
        response.put("unscheduledTasks", unscheduledTasksDTO);
        return response;
    }

    //TODO: Implement recurring tasks!
//    public void createAllRecurringTasks() {
        // Get tomorrow's date
//        LocalDate newDueDate = LocalDate.now().plusDays(1);
        // Call the repository method to create recurring tasks with updated due dates
//        taskRepository.createRecurringTasks(newDueDate);
//    }


//    public Page<TaskDTO> getAllTasks(Long userId, Pageable pageable) {
//        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
//                .map(t -> modelMapper.map(t, TaskDTO.class));
//    }
}
