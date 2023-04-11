package com.successfulliferestapi.Admin.repositories;

import com.successfulliferestapi.Admin.models.entities.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppSettingsRepository extends JpaRepository<SettingsEntity,Long> {
    SettingsEntity findBySettingName(String name);
}
