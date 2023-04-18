package com.successfulliferestapi.Admin.controllers;

import com.successfulliferestapi.Admin.constants.SettingsNames;
import com.successfulliferestapi.Admin.models.dtos.EditUserDTO;
import com.successfulliferestapi.Admin.models.dtos.SetAllowRegistrationsDTO;
import com.successfulliferestapi.Admin.services.AdminService;
import com.successfulliferestapi.Admin.services.AppSettingsService;
import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.models.enums.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PatchMapping("/users/{userId}/edit")
    public ResponseEntity<?> editUser(@PathVariable Long userId,
                                      @Valid @RequestBody EditUserDTO editUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            return ResponseEntity.ok(adminService.editUser(userId,editUserDTO));
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

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(adminService.getUserById(userId));
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
