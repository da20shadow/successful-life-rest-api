package com.successfulliferestapi.Admin.controllers;

import com.successfulliferestapi.Admin.constants.SettingsNames;
import com.successfulliferestapi.Admin.models.dtos.SetAllowRegistrationsDTO;
import com.successfulliferestapi.Admin.services.AdminService;
import com.successfulliferestapi.Admin.services.AppSettingsService;
import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AppSettingsService appSettingsService;

    @PatchMapping("/settings/allow-registrations")
    public ResponseEntity<?> setAllowNewRegistrations(@RequestBody SetAllowRegistrationsDTO request) {
        try {

            return ResponseEntity.ok(
                    appSettingsService.updateAppSetting(
                            SettingsNames.ALLOW_REGISTRATIONS,
                            request.getAllowRegistrations())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        try {
            return ResponseEntity.ok(appSettingsService.getSettings());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "24") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            return ResponseEntity.ok(adminService.getUsers(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @GetMapping("/{userId}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(adminService.banUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }
}
