package com.successfulliferestapi.Config;

import com.successfulliferestapi.Shared.interceptors.ConfirmAdminAuthority;
import com.successfulliferestapi.Shared.interceptors.DisabledRegistrationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {
    private final ConfirmAdminAuthority confirmAdminAuthority;
    private final DisabledRegistrationInterceptor disabledRegistrationInterceptor;

    public InterceptorsConfiguration(ConfirmAdminAuthority confirmAdminAuthority,
                                     DisabledRegistrationInterceptor disabledRegistrationInterceptor) {
        this.confirmAdminAuthority = confirmAdminAuthority;
        this.disabledRegistrationInterceptor = disabledRegistrationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(disabledRegistrationInterceptor);
        registry.addInterceptor(confirmAdminAuthority);
    }
}
