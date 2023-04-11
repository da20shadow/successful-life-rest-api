package com.successfulliferestapi.Admin.models.entities;

import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "settings")
public class SettingsEntity extends BaseEntity {

    @Column(name = "setting_name", unique = true)
    private String settingName;

    @Column(name = "value")
    private String value;

}
