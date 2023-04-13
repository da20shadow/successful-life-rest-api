package com.successfulliferestapi.Shared.services;

import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.repositories.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CleaningService {

    private final GoalRepository goalRepository;

    @Transactional
    public void deleteExpiredTrashGoals() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
//        goalRepository.deleteExpiredTrashGoals(thirtyDaysAgo);
        List<Goal> goalsToDelete = goalRepository.getAllDeletedExpiredTrashGoals(thirtyDaysAgo);
        goalRepository.deleteAll(goalsToDelete);
    }
}
