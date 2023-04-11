package com.successfulliferestapi.Shared.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.successfulliferestapi.Admin.services.AdminService;
import com.successfulliferestapi.JWT.services.JwtService;
import com.successfulliferestapi.Task.services.TaskService;
import com.successfulliferestapi.User.models.entity.User;
import com.successfulliferestapi.User.models.enums.UserRole;
import com.successfulliferestapi.User.repositories.UserRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ConfirmAdminAuthority implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final JwtService jwtService;
    private static final String MESSAGE_JSON = "{\"message\":\"You have no rights to access this. Your account is banned!\"}";
    private static final int RESPONSE_STATUS = 400;
    private static final String RESPONSE_CONTENT_TYPE = "application/json";
    private static final String RESPONSE_CHARACTER_ENCODING = "UTF-8";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        if (request.getRequestURL().indexOf("/management/api/v1") != -1) {
            LOGGER.info("###### RUNS Confirm admin Authority ########"); //TODO: remove it
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                writeResponse(response);
                return false;
            }

            String token = authorizationHeader.substring(7);
            String username = jwtService.extractUsername(token);
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                writeResponse(response);
                return false;
            }
            boolean hasAuthority = optionalUser.get().getRole().name().equals(UserRole.ADMIN.name());

            if (!hasAuthority) {
                adminService.banUser(optionalUser.get().getId());
                writeResponse(response);
                return false;
            }
        }
        return true;
    }

    private void writeResponse(HttpServletResponse response) throws IOException {
        response.setStatus(ConfirmAdminAuthority.RESPONSE_STATUS);
        response.setContentType(ConfirmAdminAuthority.RESPONSE_CONTENT_TYPE);
        response.setCharacterEncoding(ConfirmAdminAuthority.RESPONSE_CHARACTER_ENCODING);
        response.getWriter().write(ConfirmAdminAuthority.MESSAGE_JSON);
    }
}
