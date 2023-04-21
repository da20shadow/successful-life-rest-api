package com.successfulliferestapi.Task.repositories;

import com.successfulliferestapi.Task.models.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    // Retrieve count of tasks by targetId
    int countByTargetId(Long targetId);

    // Retrieve count of completed tasks by targetId
    @Query("SELECT COUNT(t) FROM Task t WHERE t.target.id = :targetId AND t.status = 'COMPLETED' AND t.deleted = FALSE")
    int countCompletedByTargetId(Long targetId);

    // Retrieve completed tasks by targetId
    @Query("SELECT t FROM Task t WHERE t.target.id = :targetId AND t.status = 'COMPLETED' AND t.deleted = FALSE")
    Page<Task> findCompletedByTargetId(Long targetId, Pageable pageable);

    //GET Task by title, target ID, and user ID
    Optional<Task> findByTitleAndTargetIdAndUser_IdAAndDeletedFalse(String title, Long targetId, Long userId);

    //GET Target Tasks
    Page<Task> findByUserIdAndTargetIdAndDeletedFalse(Long userId, Long targetId, Pageable pageable);

    //GET Task by ID
    Optional<Task> findByIdAndUserIdAndDeletedFalse(Long taskId, Long userId);

    //GET today tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('DATE', t.dueDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) < :today AND FUNCTION('DATE', t.dueDate) > :today) " +
            "AND t.status <> 'COMPLETED' AND t.deleted = FALSE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findNotCompletedTodayTasks(Long userId, LocalDate today);

    //Get Tasks By DATE
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('DATE', t.dueDate) = :date " +
            "OR FUNCTION('DATE', t.startDate) = :date " +
            "OR FUNCTION('DATE', t.startDate) < :date AND FUNCTION('DATE', t.dueDate) > :date) " +
            "AND t.status <> 'COMPLETED' AND t.deleted = FALSE" +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findNotCompletedTasksByDate(Long userId, LocalDate date);

    //GET Week Tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE ((t.dueDate BETWEEN :dueDate AND :dueDate2) " +
            "OR (t.startDate BETWEEN :startDate AND :startDate2)) " +
            "AND t.status <> 'COMPLETED' AND t.deleted = FALSE ")
    List<Task> findAllByUserIdAndWeek(Long userId, LocalDateTime dueDate, LocalDateTime dueDate2,
                                      LocalDateTime startDate, LocalDateTime startDate2);

    //GET Month Tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('YEAR', t.dueDate) = :year AND FUNCTION('MONTH', t.dueDate) = :month " +
            "OR FUNCTION('YEAR', t.startDate) = :year AND FUNCTION('MONTH', t.startDate) = :month ) " +
            "AND t.status <> 'COMPLETED' AND t.deleted = FALSE")
    List<Task> findAllByUserIdAndMonth(Long userId, int year, int month);


    //GET all urgent tasks
    Page<Task> findByUserIdAndUrgentTrue(Long userId, Pageable pageable);

    //Get today important tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE (FUNCTION('DATE', t.dueDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) = :today " +
            "OR FUNCTION('DATE', t.startDate) < :today AND FUNCTION('DATE', t.dueDate) > :today) " +
            "AND t.status <> 'COMPLETED' AND t.important = TRUE AND t.deleted = FALSE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findTodayImportantTasks(Long userId, LocalDate today, Pageable pageable);

    //Get Important Overdue tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE FUNCTION('DATE', t.dueDate) < :today " +
            "AND t.status <> 'COMPLETED' AND t.important = TRUE AND t.deleted = FALSE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findAllOverdueImportantTasks(Long userId, LocalDate today, Pageable pageable);

    //Get Next Important tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' AND t.important = TRUE AND t.deleted = FALSE " +
            "AND (function('date',t.startDate) > :today " +
            "OR function('date',t.dueDate) > :today AND t.startDate IS NULL) " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findAllNextImportantTasks(Long userId, LocalDate today, Pageable pageable);

    //Get unscheduled important tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' AND t.important = TRUE AND t.deleted = FALSE " +
            "AND t.dueDate IS NULL " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    Page<Task> findAllUnscheduledImportantTasks(Long userId, Pageable pageable);

    //Get Overdue tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE FUNCTION('DATE', t.dueDate) < :today " +
            "AND t.status <> 'COMPLETED' AND t.deleted = FALSE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findAllOverdueTasks(Long userId, LocalDate today);

    //Get Next tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' AND t.deleted = FALSE " +
            "AND (function('date',t.startDate) > :today " +
            "OR function('date',t.dueDate) > :today AND t.startDate IS NULL) " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findAllNextTasks(Long userId, LocalDate today);

    //Get unscheduled tasks
    @Query("SELECT t FROM Task t JOIN t.user u ON u.id = :userId " +
            "WHERE t.status <> 'COMPLETED' AND t.dueDate IS NULL AND t.deleted = FALSE " +
            "ORDER BY t.dueDate, t.priority DESC, t.urgent DESC")
    List<Task> findAllUnscheduledTasks(Long userId);

    //TODO: Create recurring tasks
//    @Modifying
//    @Transactional
//    @Query("INSERT INTO Task (description, dueDate, isRecurring, target) " +
//            "SELECT t.description, :newDueDate, true, t.target " +
//            "FROM Task t " +
//            "WHERE t.isRecurring = true")
//    void createRecurringTasks(LocalDate newDueDate);

    //Change is deleted
//    @Modifying
//    @Query("UPDATE Task t SET t.deleted = :isDeleted " +
//            "WHERE t.user.id = :userId AND t.target.goal.id = :goalId")
//    void changeTasksDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted);

    @Modifying
    @Query(value = "UPDATE tasks t " +
            "JOIN targets tar ON t.target_id = tar.id " +
            "JOIN goals g ON tar.goal_id = g.id " +
            "SET t.deleted = :isDeleted, t.deleted_at = NOW() " +
            "WHERE g.user_id = :userId AND g.id = :goalId", nativeQuery = true)
    void changeTasksDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted);


}
