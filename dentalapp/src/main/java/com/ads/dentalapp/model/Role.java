package com.ads.dentalapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    PATIENT (
            Set.of(com.ads.dentalapp.model.Permission.DENTIST_READ)
    ),
    OFFICE_MANAGER (
            Set.of(Permission.OFFICE_MANAGER_WRITE, Permission.OFFICE_MANAGER_READ)
    ),
    DENTIST (
            Set.of(Permission.DENTIST_WRITE, Permission.DENTIST_READ)
    );

    @Getter
    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+name()));
        return authorities;
    }

}
