package com.artconnect.backend.model.user;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.artconnect.backend.model.user.Permission;
import com.artconnect.backend.model.user.Role;

import java.util.Collections;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Set;

import static com.artconnect.backend.model.user.Permission.*;
import static org.junit.jupiter.api.Assertions.*;


class RoleTests {
    @Test
    void testUserPermissions() {
        Role userRole = Role.USER;
        Set<Permission> expectedPermissions = Collections.emptySet();

        assertEquals(expectedPermissions, userRole.getPermissions());
    }

    @Test
    void testAdminPermissions() {
        Role adminRole = Role.ADMIN;
        Set<Permission> expectedPermissions = Set.of(
                ADMIN_READ,
                ADMIN_UPDATE,
                ADMIN_DELETE,
                ADMIN_CREATE,
                MANAGER_READ,
                MANAGER_UPDATE,
                MANAGER_DELETE,
                MANAGER_CREATE
        );

        assertEquals(expectedPermissions, adminRole.getPermissions());
    }

    @Test
    void testManagerPermissions() {
        Role managerRole = Role.MANAGER;
        Set<Permission> expectedPermissions = Set.of(
                MANAGER_READ,
                MANAGER_UPDATE,
                MANAGER_DELETE,
                MANAGER_CREATE
        );

        assertEquals(expectedPermissions, managerRole.getPermissions());
    }

    @Test
    void testUserAuthorities() {
        Role userRole = Role.USER;
        List<SimpleGrantedAuthority> expectedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        assertEquals(expectedAuthorities, userRole.getAuthorities());
    }

    @Test
    void testAdminAuthorities() {
        Role adminRole = Role.ADMIN;
        Set<Permission> expectedPermissions = Set.of(
                ADMIN_READ,
                ADMIN_UPDATE,
                ADMIN_DELETE,
                ADMIN_CREATE,
                MANAGER_READ,
                MANAGER_UPDATE,
                MANAGER_DELETE,
                MANAGER_CREATE
        );

        List<SimpleGrantedAuthority> expectedAuthorities = expectedPermissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        expectedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        assertEquals(expectedAuthorities, adminRole.getAuthorities());
    }

    @Test
    void testManagerAuthorities() {
        Role managerRole = Role.MANAGER;
        Set<Permission> expectedPermissions = Set.of(
                MANAGER_READ,
                MANAGER_UPDATE,
                MANAGER_DELETE,
                MANAGER_CREATE
        );

        List<SimpleGrantedAuthority> expectedAuthorities = expectedPermissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        expectedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

        assertEquals(expectedAuthorities, managerRole.getAuthorities());
    }
}