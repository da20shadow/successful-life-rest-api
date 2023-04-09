package com.successfulliferestapi.User.services;

import com.successfulliferestapi.JWT.services.JwtService;
import com.successfulliferestapi.User.constants.UserMessages;
import com.successfulliferestapi.User.exceptions.UserException;
import com.successfulliferestapi.User.models.dto.LoginRequestDTO;
import com.successfulliferestapi.User.models.dto.LoginSuccessResponseDTO;
import com.successfulliferestapi.User.models.dto.RegisterRequestDTO;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.models.enums.UserRole;
import com.successfulliferestapi.User.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequestDTO request) {
        // Check if the email is already registered
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(UserMessages.Error.EMAIL_ALREADY_EXIST);
        }

        int atIndex = request.getEmail().indexOf("@"); // Get the index of '@' symbol
        String username = request.getEmail().substring(0, atIndex); // Get the substring from start to '@' symbol

        // Create a new user entity from the register request DTO
        var user = User.builder()
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        try {
            // Save the new user to the UserRepository
            userRepository.save(user);
            return UserMessages.Success.REGISTRATION;
        } catch (Exception e) {
            throw new UserException(UserMessages.Error.REGISTRATION);
        }
    }

    public LoginSuccessResponseDTO login(LoginRequestDTO request) {

        //Check if the user exist
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(UserMessages.Error.LOGIN));

        //Verify the user password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException(UserMessages.Error.LOGIN);
        }
        //Generate jwt token
        var jwtToken = jwtService.generateToken(user);
        jwtService.revokeAllUserTokens(user);
        jwtService.saveUserToken(user, jwtToken);

        return new LoginSuccessResponseDTO(
                UserMessages.Success.LOGIN,
                jwtToken,
                user.getFirstName(),
                user.getEmail(),
                user.getRole());
    }
}
