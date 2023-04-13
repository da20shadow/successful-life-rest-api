package com.successfulliferestapi.Idea.repositories;

import com.successfulliferestapi.Idea.models.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea,Long> {
    Optional<Idea> findByIdAndUserId(Long ideaId, Long userId);

    Optional<Idea> findByTitle(String title);

    List<Idea> findAllByUserId(Long userId);

    Page<Idea> findAllByUserId(Long userId, Pageable pageable);

    Page<Idea> findByUserIdAndTags_Id(Long userId, Long id, Pageable pageable);

    List<Idea> findAllByUserIdAndGoalId(Long userId, Long goalId);

    Page<Idea> findByUserIdAndGoalId(Long userId, Long goalId, Pageable pageable);

    Page<Idea> findByUserIdAndGoalIdAndTags_Id(Long userId, Long goalId, Long tagId, Pageable pageable);

    //Change is deleted
    @Modifying
    @Query("UPDATE Idea i SET i.deleted = :isDeleted, i.deletedAt = :now " +
            "WHERE i.user.id = :userId AND i.goal.id = :goalId")
    void changeIdeasDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted, LocalDateTime now);
}
