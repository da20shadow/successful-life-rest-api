package com.successfulliferestapi.User.controllers;

import com.successfulliferestapi.Shared.models.dto.ErrorResponseDTO;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.models.dto.LoginRequestDTO;
import com.successfulliferestapi.User.models.dto.LoginSuccessResponseDTO;
import com.successfulliferestapi.User.models.dto.RegisterRequestDTO;
import com.successfulliferestapi.User.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            // If there are validation errors, return them with a 400 Bad Request status code
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errors.get(0)));
        }
        // If the request is valid, call the register method on the AuthService and return the result
        try {
            SuccessResponseDTO message = new SuccessResponseDTO(authService.register(request));
            return ResponseEntity.status(201).body(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request, BindingResult result) {
        if (result.hasErrors()) {
            // If there are validation errors, return the last error message with a 400 Bad Request status code
            String errorMessage = result.getAllErrors().get(result.getAllErrors().size() - 1).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(errorMessage));
        }
        // If the request is valid, call the login method on the AuthService and return the success message
        try {
            LoginSuccessResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
    }

}
