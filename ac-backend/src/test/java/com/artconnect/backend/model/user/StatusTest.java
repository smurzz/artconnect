package com.artconnect.backend.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void testEnumValues() {
        Status[] values = Status.values();

        Assertions.assertEquals(3, values.length);
        Assertions.assertArrayEquals(new Status[]{Status.PUBLIC, Status.PRIVATE, Status.RESTRICTED}, values);
    }

    @Test
    public void testEnumToString() {
        Assertions.assertEquals("PUBLIC", Status.PUBLIC.toString());
        Assertions.assertEquals("PRIVATE", Status.PRIVATE.toString());
        Assertions.assertEquals("RESTRICTED", Status.RESTRICTED.toString());
    }

    @Test
    public void testEnumValueOf() {
        Assertions.assertEquals(Status.PUBLIC, Status.valueOf("PUBLIC"));
        Assertions.assertEquals(Status.PRIVATE, Status.valueOf("PRIVATE"));
        Assertions.assertEquals(Status.RESTRICTED, Status.valueOf("RESTRICTED"));
    }

    @Test
    public void testEnumOrdinal() {
        Assertions.assertEquals(0, Status.PUBLIC.ordinal());
        Assertions.assertEquals(1, Status.PRIVATE.ordinal());
        Assertions.assertEquals(2, Status.RESTRICTED.ordinal());
    }

    @Test
    public void testEnumEquals() {
        Assertions.assertEquals(Status.PUBLIC, Status.PUBLIC);
        Assertions.assertEquals(Status.PRIVATE, Status.PRIVATE);
        Assertions.assertEquals(Status.RESTRICTED, Status.RESTRICTED);

        Assertions.assertNotEquals(Status.PUBLIC, Status.PRIVATE);
        Assertions.assertNotEquals(Status.PUBLIC, Status.RESTRICTED);
        Assertions.assertNotEquals(Status.PRIVATE, Status.RESTRICTED);
    }

    @Test
    public void testEnumHashCode() {
        Assertions.assertEquals(Status.PUBLIC.hashCode(), Status.PUBLIC.hashCode());
        Assertions.assertEquals(Status.PRIVATE.hashCode(), Status.PRIVATE.hashCode());
        Assertions.assertEquals(Status.RESTRICTED.hashCode(), Status.RESTRICTED.hashCode());

        Assertions.assertNotEquals(Status.PUBLIC.hashCode(), Status.PRIVATE.hashCode());
        Assertions.assertNotEquals(Status.PUBLIC.hashCode(), Status.RESTRICTED.hashCode());
        Assertions.assertNotEquals(Status.PRIVATE.hashCode(), Status.RESTRICTED.hashCode());
    }

    @Test
    public void testEnumInstance() {
        Status publicStatus1 = Status.PUBLIC;
        Status publicStatus2 = Status.PUBLIC;
        Assertions.assertSame(publicStatus1, publicStatus2);

        Status privateStatus = Status.PRIVATE;
        Assertions.assertNotSame(publicStatus1, privateStatus);
    }
}