package com.successfulliferestapi.User.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.successfulliferestapi.Admin.services.AppSettingsService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DisabledRegistrationInterceptor implements HandlerInterceptor {
    private final AppSettingsService appSettingsService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        boolean isRegistrationEnabled = appSettingsService.isRegistrationEnabled();

        if (request.getRequestURL().indexOf("/api/v1/auth/register") != -1 && !isRegistrationEnabled) {
            response.setStatus(400);
            response.setContentType("application/json"); // Set response content type to JSON
            response.setCharacterEncoding("UTF-8"); // Set response character encoding to UTF-8

            // Create a JSON object with the desired message
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(
                    Collections.singletonMap("message", "User registrations not allowed at this time"));

            // Write the JSON string as response
            response.getWriter().write(json);

            return false;
        }
        return true;
    }

}
