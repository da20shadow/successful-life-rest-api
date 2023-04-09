package com.successfulliferestapi.Target.services;

import com.successfulliferestapi.Goal.constants.GoalMessages;
import com.successfulliferestapi.Goal.exceptions.GoalException;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.repositories.GoalRepository;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.Target.constants.TargetMessages;
import com.successfulliferestapi.Target.exceptions.TargetException;
import com.successfulliferestapi.Target.models.dto.*;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Target.repositories.TargetRepository;
import com.successfulliferestapi.Task.repositories.TaskRepository;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TargetService {
    private final TargetRepository targetRepository;
    private final TaskRepository taskRepository;
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    public AddTargetSuccessResponseDTO add(User user, AddTargetDTO addTargetDTO) {
        Goal goal = goalRepository.findByIdAndUserId(addTargetDTO.getGoalId(), user.getId())
                .orElseThrow(() -> new GoalException(GoalMessages.Error.NOT_FOUND));

        Optional<Target> existingTarget = targetRepository.findByTitleAndGoalIdAndUserId(addTargetDTO.getTitle(), goal.getId(), user.getId());

        if (existingTarget.isPresent()) {
            throw new TargetException(TargetMessages.Error.DUPLICATE_TITLE);
        }

        Target target = Target.builder()
                .title(addTargetDTO.getTitle())
                .description(addTargetDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .goal(goal)
                .build();

        Target addedTarget = targetRepository.save(target);

        TargetDTO targetDTO = modelMapper.map(addedTarget, TargetDTO.class);

        return new AddTargetSuccessResponseDTO(TargetMessages.Success.ADDED,targetDTO);
    }

    public UpdateTargetSuccessResponseDTO changeTitle(Long userId, Long targetId, UpdateTargetTitleDTO updateTargetTitleDTO) {
        Optional<Target> targetDB = targetRepository.findByIdAndUserId(targetId, userId);
        if (targetDB.isEmpty()) {
            throw new TargetException(TargetMessages.Error.NOT_FOUND);
        }
        Target target = targetDB.get();
        target.setTitle(updateTargetTitleDTO.getTitle());
        Target updatedTarget = targetRepository.save(target);
        TargetDTO targetDTO = modelMapper.map(updatedTarget,TargetDTO.class);
        return new UpdateTargetSuccessResponseDTO(TargetMessages.Success.UPDATED_TITLE,targetDTO);
    }

    public UpdateTargetSuccessResponseDTO changeDescription(Long userId, Long targetId, UpdateTargetDescriptionDTO updateTargetDescriptionDTO) {
        Optional<Target> targetDB = targetRepository.findByIdAndUserId(targetId, userId);
        if (targetDB.isEmpty()) {
            throw new TargetException(TargetMessages.Error.NOT_FOUND);
        }
        Target target = targetDB.get();
        target.setDescription(updateTargetDescriptionDTO.getDescription());
        Target updatedTarget = targetRepository.save(target);
        TargetDTO targetDTO = modelMapper.map(updatedTarget,TargetDTO.class);
        return new UpdateTargetSuccessResponseDTO(TargetMessages.Success.UPDATED_DESCRIPTION,targetDTO);
    }

    @Transactional
    public TargetDTO getById(Long targetId, Long userId) {
        Optional<Target> targetDB = targetRepository.findByIdAndUserId(targetId,userId);
        if (targetDB.isEmpty()) {
            throw new TargetException(TargetMessages.Error.NOT_FOUND);
        }
        TargetDTO targetDTO = modelMapper.map(targetDB.get(), TargetDTO.class);
        targetDTO.setGoalId(targetDB.get().getGoal().getId());
        return targetDTO;
    }

    public List<TargetDTO> getAllByGoalId(Long goalId, Long userId, Pageable pageable) {
        //TODO: Make it pageable and return Page<TargetDTO>!
        // Page<Target> targetPageDB = targetRepository.findAllByGoalIdAndUserId(goalId, userId, pageable);
        List<Target> targetsDB = targetRepository.findAllByGoalIdAndUserId(goalId, userId);
        List<TargetDTO> targets = targetsDB.stream().map(t -> {
            int totalTasks = taskRepository.countByTargetId(t.getId());
            int totalCompletedTasks = taskRepository.countCompletedByTargetId(t.getId());
            TargetDTO target = modelMapper.map(t, TargetDTO.class);
            target.setTotalTasks(totalTasks);
            target.setTotalCompletedTasks(totalCompletedTasks);
            return target;
        }).collect(Collectors.toList());

        return targets;
    }

    public SuccessResponseDTO deleteTarget(Long targetId, Long userId) {
        Target target = targetRepository.findByIdAndUserId(targetId, userId)
                .orElseThrow(() -> new TargetException(TargetMessages.Error.NOT_FOUND));
        targetRepository.delete(target);
        return new SuccessResponseDTO(TargetMessages.Success.DELETED);
    }
}
