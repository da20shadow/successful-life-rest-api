package com.successfulliferestapi.task.models.entity;

import com.successfulliferestapi.shared.models.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checklists", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "title"})
})
public class ChecklistItem extends BaseEntity {
}
