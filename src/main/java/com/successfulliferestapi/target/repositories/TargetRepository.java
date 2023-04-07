package com.successfulliferestapi.target.repositories;

import com.successfulliferestapi.target.models.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target,Long> {
}
