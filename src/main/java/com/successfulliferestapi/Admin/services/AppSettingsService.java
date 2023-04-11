package com.successfulliferestapi.Admin.services;

import com.successfulliferestapi.Admin.models.entities.SettingsEntity;
import com.successfulliferestapi.Admin.repositories.AppSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppSettingsService {

    private final AppSettingsRepository appSettingsRepository;

    public Boolean isRegistrationEnabled() {
        return getAppSetting("allow_registration").equals("true");
    }

    public String getAppSetting(String settingName) {
        SettingsEntity appSetting = appSettingsRepository.findBySettingName(settingName);
        return appSetting != null ? appSetting.getValue() : null;
    }

    public void updateAppSetting(String settingName, String value) {
        SettingsEntity appSetting = appSettingsRepository.findBySettingName(settingName);
        if (appSetting != null) {
            appSetting.setValue(value);
            appSettingsRepository.save(appSetting);
        }
    }
}
