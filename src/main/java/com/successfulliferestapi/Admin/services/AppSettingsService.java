package com.successfulliferestapi.Admin.services;

import com.successfulliferestapi.Admin.models.entities.SettingsEntity;
import com.successfulliferestapi.Admin.repositories.AppSettingsRepository;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppSettingsService {

    private final AppSettingsRepository appSettingsRepository;

    public Boolean isRegistrationEnabled() {
        return getAppSetting("allow_registrations").equals("true");
    }

    public String getAppSetting(String settingName) {
        SettingsEntity appSetting = appSettingsRepository.findBySettingName(settingName);
        return appSetting != null ? appSetting.getValue() : null;
    }

    public SuccessResponseDTO updateAppSetting(String settingName, String value) {
        SettingsEntity appSetting = appSettingsRepository.findBySettingName(settingName);

        if (appSetting == null) {
            throw new RuntimeException("There is no such settings!");
        }
        appSetting.setValue(value);
        appSettingsRepository.save(appSetting);
        return new SuccessResponseDTO("Settings Updated Successfully!");
    }

    public List<SettingsEntity> getSettings() {
        List<SettingsEntity> settings = appSettingsRepository.findAll();
        return settings;
    }
}
