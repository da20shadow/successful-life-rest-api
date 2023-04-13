package com.successfulliferestapi.Shared.schedulers;

import com.successfulliferestapi.Shared.services.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanTrash {

    private final CleaningService cleaningService;

//    @Scheduled(cron = "0 0 0 L * *") // run at midnight on the last day of the month
//    @Scheduled(fixedRate = 120000) // 120 seconds
    @Scheduled(cron = "0 0 0 * * *") // every 24 hours
    public void runTask() {
        cleaningService.deleteExpiredTrashGoals();
        System.out.println("CALL CLEAR EXPIRED GOALS IN TRASH!");
    }
}
