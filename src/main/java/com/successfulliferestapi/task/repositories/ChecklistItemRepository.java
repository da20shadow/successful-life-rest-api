package com.successfulliferestapi.Task.repositories;

import com.successfulliferestapi.Task.models.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem,Long> {
}
