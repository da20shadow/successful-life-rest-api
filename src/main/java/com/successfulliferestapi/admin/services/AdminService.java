package com.successfulliferestapi.Admin.services;

import com.successfulliferestapi.Admin.constants.AdminMessages;
import com.successfulliferestapi.Admin.models.dtos.EditUserDTO;
import com.successfulliferestapi.Admin.models.dtos.SetAllowRegistrationsDTO;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.constants.UserMessages;
import com.successfulliferestapi.User.exceptions.UserException;
import com.successfulliferestapi.User.models.dto.UserDTO;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.models.enums.UserRole;
import com.successfulliferestapi.User.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public SuccessResponseDTO banUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(UserMessages.Error.NOT_FOUND);
        }
        User user = optionalUser.get();
        user.setBanned(true);
        userRepository.save(user);
        return new SuccessResponseDTO(AdminMessages.Success.BANNED_USER);
    }

    public Page<UserDTO> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findByRole(UserRole.USER, pageable);
        return usersPage.map(user -> modelMapper.map(user, UserDTO.class));
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(UserMessages.Error.NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public SuccessResponseDTO editUser(Long userId, EditUserDTO editUserDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(UserMessages.Error.NOT_FOUND);
        }
        String message = "";
        User user = optionalUser.get();

        if (editUserDTO.getEmail() != null) {
            user.setEmail(editUserDTO.getEmail());
            message = UserMessages.Success.UPDATED_EMAIL;
        }else if (editUserDTO.getUsername() != null) {
            user.setUsername(editUserDTO.getUsername());
            message = UserMessages.Success.UPDATED_USERNAME;
        }else if (editUserDTO.getFirstName() != null) {
            user.setFirstName(editUserDTO.getFirstName());
            message = UserMessages.Success.UPDATED_FIRSTNAME;
        }else if (editUserDTO.getLastName() != null) {
            user.setLastName(editUserDTO.getLastName());
            message = UserMessages.Success.UPDATED_LASTNAME;
        }

        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        System.out.println(user.getRole());

        User updatedUser = userRepository.save(user);

        return new SuccessResponseDTO(message);
    }
}
