package com.successfulliferestapi.Task.services;

import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.Task.constants.TaskMessages;
import com.successfulliferestapi.Task.exceptions.TaskException;
import com.successfulliferestapi.Task.models.dto.AddChecklistItemDTO;
import com.successfulliferestapi.Task.models.dto.ChecklistItemDTO;
import com.successfulliferestapi.Task.models.dto.ChecklistItemSuccessResponseDTO;
import com.successfulliferestapi.Task.models.dto.EditChecklistItemDTO;
import com.successfulliferestapi.Task.models.entity.ChecklistItem;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.Task.repositories.ChecklistRepository;
import com.successfulliferestapi.Task.repositories.TaskRepository;
import com.successfulliferestapi.User.models.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final ChecklistRepository checklistItemRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public ChecklistItemSuccessResponseDTO add(Long taskId, User user, AddChecklistItemDTO checklistItem) {
        Optional<ChecklistItem> checklistItemDB =
                checklistItemRepository.findByTitleAndTaskIdAndUserId(checklistItem.getTitle(),taskId,user.getId());
        if (checklistItemDB.isPresent()) {
            throw new TaskException(TaskMessages.Error.DUPLICATE_CHECKLIST_ITEM_TITLE);
        }

        Optional<Task> taskFromDB = taskRepository.findByIdAndUserId(taskId,user.getId());
        if (taskFromDB.isEmpty()) {
            throw new TaskException(TaskMessages.Error.NOT_FOUND);
        }

        Task task = taskFromDB.get();
        ChecklistItem item = ChecklistItem.builder()
                .title(checklistItem.getTitle())
                .completed(false)
                .user(user)
                .task(task)
                .build();
        ChecklistItemDTO addedItem = modelMapper.map(checklistItemRepository.save(item),ChecklistItemDTO.class);
        return new ChecklistItemSuccessResponseDTO(TaskMessages.Success.ADDED_CHECKLIST_ITEM,addedItem);
    }

    public List<ChecklistItemDTO> getAllByTaskId(Long taskId, Long userId) {
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByTaskIdAndUserId(taskId, userId);
        return checklistItems.stream()
                .map(checklistItem -> modelMapper.map(checklistItem, ChecklistItemDTO.class))
                .collect(Collectors.toList());
    }

    public SuccessResponseDTO deleteItem(Long itemId, Long userId) {
        Optional<ChecklistItem> item = checklistItemRepository.findByIdAndUserId(itemId, userId);
        if (item.isEmpty()) {
            throw new TaskException(TaskMessages.Error.CHECKLIST_ITEM_NOT_FOUND);
        }
        checklistItemRepository.delete(item.get());
        return new SuccessResponseDTO(TaskMessages.Success.DELETED_CHECKLIST_ITEM);
    }

    public ChecklistItemSuccessResponseDTO editItem(Long itemId, Long userId, EditChecklistItemDTO item) {
        Optional<ChecklistItem> itemFromDB = checklistItemRepository.findByIdAndUserId(itemId, userId);
        if (itemFromDB.isEmpty()) {
            throw new TaskException(TaskMessages.Error.CHECKLIST_ITEM_NOT_FOUND);
        }

        boolean hasChanges = false;
        ChecklistItem itemToUpdate = itemFromDB.get();

        if (item.getTitle() != null) {
            itemToUpdate.setTitle(item.getTitle());
            hasChanges = true;
        }

        boolean isCompleted = Objects.requireNonNullElse(item.isCompleted(), itemToUpdate.isCompleted());

        if (isCompleted != itemToUpdate.isCompleted()) {
            itemToUpdate.setCompleted(isCompleted);
            hasChanges = true;
        }

        if (!hasChanges) {
            throw new TaskException(TaskMessages.Error.NO_CHANGES);
        }

        ChecklistItemDTO updatedItem = modelMapper.map(checklistItemRepository.save(itemToUpdate),ChecklistItemDTO.class);
        return new ChecklistItemSuccessResponseDTO(TaskMessages.Success.UPDATED_CHECKLIST_ITEM,updatedItem);
    }

    public SuccessResponseDTO markAllAsCompleted(Long taskId, Long userId) {
        List<ChecklistItem> checklistItems = checklistItemRepository.findByTaskIdAndUserId(taskId, userId);
        for (ChecklistItem item : checklistItems) {
            item.setCompleted(true);
        }
        checklistItemRepository.saveAll(checklistItems);
        return new SuccessResponseDTO("All checklist items marked as completed");
    }
}
