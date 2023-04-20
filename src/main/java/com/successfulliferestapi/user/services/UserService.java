package com.successfulliferestapi.User.services;

import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.constants.UserMessages;
import com.successfulliferestapi.User.exceptions.UserException;
import com.successfulliferestapi.User.models.dto.*;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuccessResponseDTO editEmail(User user, ChangeEmailRequestDTO requestDTO) {
        user.setEmail(requestDTO.getEmail());
        userRepository.save(user);
        return new SuccessResponseDTO(UserMessages.Success.UPDATED_EMAIL);
    }

    public SuccessResponseDTO editUsername(User user, ChangeUsernameRequestDTO requestDTO) {

        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new UserException(UserMessages.Error.USERNAME_ALREADY_TAKEN);
        }
        if (user.getUsername().equals(requestDTO.getUsername())) {
            throw new UserException(UserMessages.Error.SAME_USERNAME);
        }

        user.setUsername(requestDTO.getUsername());
        userRepository.save(user);
        return new SuccessResponseDTO(UserMessages.Success.UPDATED_USERNAME);
    }

    public SuccessResponseDTO editPassword(User user, ChangePasswordRequestDTO requestDTO) {

        //Verify the user password
        if (!passwordEncoder.matches(requestDTO.getOldPassword(), user.getPassword())) {
            throw new UserException(UserMessages.Error.INVALID_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        userRepository.save(user);
        return new SuccessResponseDTO(UserMessages.Success.UPDATED_PASSWORD);
    }

    public SuccessResponseDTO editNames(User user, ChangeNamesRequestDTO requestDTO) {
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        userRepository.save(user);
        return new SuccessResponseDTO(UserMessages.Success.UPDATED_NAMES);
    }

    public SuccessResponseDTO deleteAccount(User user) {
        userRepository.delete(user);
        return new SuccessResponseDTO(UserMessages.Success.DELETED);
    }

    public ProfileDetailsDTO getProfile(User user) {
        return ProfileDetailsDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
