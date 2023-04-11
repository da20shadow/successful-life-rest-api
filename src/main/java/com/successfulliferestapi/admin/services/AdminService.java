package com.successfulliferestapi.Admin.services;

import com.successfulliferestapi.Admin.constants.AdminMessages;
import com.successfulliferestapi.Shared.models.dto.SuccessResponseDTO;
import com.successfulliferestapi.User.constants.UserMessages;
import com.successfulliferestapi.User.exceptions.UserException;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

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
}
