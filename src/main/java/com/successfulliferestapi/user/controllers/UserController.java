package com.successfulliferestapi.User.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.User.models.dto.ChangeEmailRequestDTO;
import com.successfulliferestapi.User.models.dto.ChangeNamesRequestDTO;
import com.successfulliferestapi.User.models.dto.ChangePasswordRequestDTO;
import com.successfulliferestapi.User.models.dto.ChangeUsernameRequestDTO;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Edit Email
    @PatchMapping("/edit/email")
    public ResponseEntity<?> editEmail(@Valid @RequestBody ChangeEmailRequestDTO email,
                                       BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            // If there are validation errors, return them with a 400 Bad Request status code
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.editEmail(user, email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Edit username
    @PatchMapping("/edit/username")
    public ResponseEntity<?> editUsername(@Valid @RequestBody ChangeUsernameRequestDTO username,
                                          BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            // If there are validation errors, return them with a 400 Bad Request status code
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.editUsername(user, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Edit Password
    @PatchMapping("/edit/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody ChangePasswordRequestDTO password,
                                          BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            // If there are validation errors, return them with a 400 Bad Request status code
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.editPassword(user, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Edit First and last name
    @PatchMapping("/edit/names")
    public ResponseEntity<?> editNames(@Valid @RequestBody ChangeNamesRequestDTO names,
                                       BindingResult result, Authentication authentication) {

        if (result.hasErrors()) {
            // If there are validation errors, return them with a 400 Bad Request status code
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }

        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.editNames(user, names));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Delete User
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.deleteAccount(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    //Get Profile Details
    @GetMapping("/profile")
    public ResponseEntity<?> getProfileDetails(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(userService.getProfile(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
