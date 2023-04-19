package com.successfulliferestapi.Goal.services;

import com.successfulliferestapi.Goal.constants.GoalMessages;
import com.successfulliferestapi.Goal.exceptions.GoalException;
import com.successfulliferestapi.Goal.models.dto.*;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import com.successfulliferestapi.Goal.repositories.GoalRepository;
import com.successfulliferestapi.Idea.models.entity.Idea;
import com.successfulliferestapi.Idea.repositories.IdeaRepository;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Target.repositories.TargetRepository;
import com.successfulliferestapi.Task.models.entity.ChecklistItem;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.Task.repositories.ChecklistRepository;
import com.successfulliferestapi.Task.repositories.TaskRepository;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final TargetRepository targetRepository;
    private final TaskRepository taskRepository;
    private final IdeaRepository ideaRepository;
    private final ChecklistRepository checklistRepository;
    private final ModelMapper modelMapper;

    //CREATE New Goal
    @Transactional
    public AddGoalSuccessResponseDTO add(User user, AddGoalDTO addGoalDTO) {

        if (goalRepository.existsByTitleAndUser_Id(addGoalDTO.getTitle(), user.getId())) {
            throw new GoalException(GoalMessages.Error.DUPLICATE_TITLE);
        }

        Goal goal = modelMapper.map(addGoalDTO, Goal.class);
        goal.setUser(user);
        goal.setCreatedAt(LocalDateTime.now());
        Goal createdGoal = goalRepository.save(goal);
        GoalDTO goalDTO = modelMapper.map(createdGoal, GoalDTO.class);
        goalDTO.setDeadline(createdGoal.getDeadline());
        goalDTO.setTotalCompletedTargets(0);
        goalDTO.setTotalTargets(0);
        goalDTO.setTotalIdeas(0);
        return new AddGoalSuccessResponseDTO(GoalMessages.Success.ADD, goalDTO);
    }

    //CHANGE Goal Title
    public SuccessResponseDTO changeTitle(Long userId, Long goalId, UpdateGoalTitleDTO updateGoalTitleDTO) {
        Optional<Goal> goalOptional = goalRepository.findByIdAndUserId(goalId, userId);
        if (goalOptional.isEmpty()) {
            throw new GoalException(GoalMessages.Error.NOT_FOUND);
        }
        Goal goal = goalOptional.get();
        goal.setTitle(updateGoalTitleDTO.getTitle());
        goalRepository.save(goal);
        return new SuccessResponseDTO(GoalMessages.Success.UPDATED_TITLE);
    }

    //CHANGE Goal Description
    public SuccessResponseDTO changeDescription(Long userId, Long goalId, UpdateGoalDescriptionDTO updateGoalDescriptionDTO) {
        Optional<Goal> goalOptional = goalRepository.findByIdAndUserId(goalId, userId);
        if (goalOptional.isEmpty()) {
            throw new GoalException(GoalMessages.Error.NOT_FOUND);
        }

        Goal goal = goalOptional.get();
        goal.setDescription(updateGoalDescriptionDTO.getDescription());
        goalRepository.save(goal);
        return new SuccessResponseDTO(GoalMessages.Success.UPDATED_DESCRIPTION);
    }

    //CHANGE Goal Deadline
    public SuccessResponseDTO changeDeadline(Long userId, Long goalId, UpdateGoalDeadlineDTO updateGoalDeadlineDTO) {
        Optional<Goal> goalOptional = goalRepository.findByIdAndUserId(goalId, userId);
        if (goalOptional.isEmpty()) {
            throw new GoalException(GoalMessages.Error.NOT_FOUND);
        }

        Goal goal = goalOptional.get();
        goal.setDeadline(LocalDate.parse(updateGoalDeadlineDTO.getDeadline()));
        goalRepository.save(goal);
        return new SuccessResponseDTO(GoalMessages.Success.UPDATED_DEADLINE);
    }

    //RECOVER Soft Deleted Goal
    @Transactional
    public SuccessResponseDTO recoverDeletedGoal(Long goalId, Long userId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(()-> new GoalException(GoalMessages.Error.NOT_FOUND));

        //Recover Goal and all it's Ideas targets and tasks by changing is deleted to true
        changeDeleted(goal,userId,false);
        return new SuccessResponseDTO(GoalMessages.Success.RECOVERED);
    }

    //Soft DELETE Goal
    @Transactional
    public SuccessResponseDTO deleteGoal(Long goalId, Long userId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(()-> new GoalException(GoalMessages.Error.NOT_FOUND));
        //Delete Goal and all  it's Ideas targets and tasks by changing is deleted to true
        changeDeleted(goal,userId,true);

        return new SuccessResponseDTO(GoalMessages.Success.DELETED);
    }

    //Permanent DELETE Goal
    public SuccessResponseDTO permanentDeleteGoal(Long goalId, Long userId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(()-> new GoalException(GoalMessages.Error.NOT_FOUND));
        goalRepository.delete(goal);
        return new SuccessResponseDTO(GoalMessages.Success.DELETED);
    }

    //GET All User Goals
    @Transactional
    public Page<GoalDTO> getAll(Long userId, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserId(userId,pageable);
        return getGoalDTOS(goalsPage);
    }

    //GET All User Goals By Category
    @Transactional
    public Page<GoalDTO> getAllByCategory(Long userId, GoalCategory goalCategory, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserIdAndCategory(userId, goalCategory,pageable);
        return getGoalDTOS(goalsPage);
    }

    //GET All DELETED User Goals
    @Transactional
    public Page<GoalDTO> getAllDeleted(Long userId, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserIdAndDeletedTrue(userId,pageable);
        return getGoalDTOS(goalsPage);
    }

    //GET All DELETED User Goals By Category
    @Transactional
    public Page<GoalDTO> getAllDeletedByCategory(Long userId, GoalCategory goalCategory, Pageable pageable) {
        Page<Goal> goalsPage = goalRepository.findByUserIdAndCategoryAndDeletedTrue(userId, goalCategory,pageable);
        return getGoalDTOS(goalsPage);
    }

    //GET Goal By ID
    @Transactional
    public GoalDTO getById(Long goalId, Long userId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalException(GoalMessages.Error.NOT_FOUND));
        return toGoalDTO(goal);
    }

    //Convert Page with Goals to Page with Goals DTO
    private Page<GoalDTO> getGoalDTOS(Page<Goal> goalsPage) {
        return goalsPage.map(this::toGoalDTO);
    }

    //Convert Goal to GoalDTO
    private GoalDTO toGoalDTO(Goal goalDB) {

        GoalDTO goalDTO = modelMapper.map(goalDB, GoalDTO.class);

        Set<Target> targets = goalDB.getTargets();
        int totalCompletedTargets = 0;

        for (Target target : targets) {
            List<Task> tasks = target.getTasks();
            int totalCompletedTasks = 0;
            for (Task task : tasks) {
                if (task.getStatus().getStatusName().equals("Completed")) {
                    totalCompletedTasks++;
                }
            }
            if (tasks.size() > 0 && totalCompletedTasks == tasks.size()) {
                totalCompletedTargets++;
            }
        }
        int totalIdeas = ideaRepository.countIdeasByGoalId(goalDB.getId());
        goalDTO.setTotalIdeas(totalIdeas);
        goalDTO.setTotalTargets(targets.size());
        goalDTO.setTotalCompletedTargets(totalCompletedTargets);
        return goalDTO;
    }

    //Change is Deleted
    private void changeDeleted(Goal goal, Long userId, boolean isDeleted) {
        LocalDateTime now = LocalDateTime.now();
        goal.setDeleted(isDeleted);
        goal.setDeletedAt(now);
        goalRepository.save(goal);
        targetRepository.changeTargetsDeletedByUserIdAndGoalId(userId, goal.getId(),isDeleted,now);
        ideaRepository.changeIdeasDeletedByUserIdAndGoalId(userId, goal.getId(),isDeleted,now);
        taskRepository.changeTasksDeletedByUserIdAndGoalId(userId,goal.getId(),isDeleted);
        checklistRepository.changeChecklistItemsDeletedByUserIdAndGoalId(userId, goal.getId(),isDeleted);
    }

}
