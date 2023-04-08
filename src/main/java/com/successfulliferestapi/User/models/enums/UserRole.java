package com.successfulliferestapi.User.models.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            UserPermission.MEMBER_READ,
            UserPermission.MEMBER_WRITE
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
