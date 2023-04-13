package com.successfulliferestapi.Admin.services;

import com.successfulliferestapi.Admin.constants.AdminMessages;
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

}
