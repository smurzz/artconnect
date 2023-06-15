package com.artconnect.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.artconnect.backend.model.user.Permission;

@DisplayName("Permission Tests")
class PermissionTests {

    @Test
    @DisplayName("Permission Enum Values")
    void testPermissionEnumValues() {
        Permission[] permissions = Permission.values();

        Assertions.assertEquals(8, permissions.length);
        Assertions.assertEquals("admin:read", permissions[0].getPermission());
        Assertions.assertEquals("admin:update", permissions[1].getPermission());
        Assertions.assertEquals("admin:create", permissions[2].getPermission());
        Assertions.assertEquals("admin:delete", permissions[3].getPermission());
        Assertions.assertEquals("management:read", permissions[4].getPermission());
        Assertions.assertEquals("management:update", permissions[5].getPermission());
        Assertions.assertEquals("management:create", permissions[6].getPermission());
        Assertions.assertEquals("management:delete", permissions[7].getPermission());
    }
}






