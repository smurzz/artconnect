package com.artconnect.backend.controller.request;

import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class RegisterRequestTest {


    @Test
    public void testValidRegisterRequest() {
        RegisterRequest request = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals("John", request.getFirstname());
        Assertions.assertEquals("Doe", request.getLastname());
        Assertions.assertEquals("john.doe@example.com", request.getEmail());
        Assertions.assertEquals("password123", request.getPassword());
    }




    @Test
    public void testRegisterRequestFirstname() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .build();

        Assertions.assertEquals("John", registerRequest.getFirstname());
    }


    @Test
    public void testRegisterRequestLastname() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .lastname("Doe")
                .build();

        Assertions.assertEquals("Doe", registerRequest.getLastname());
    }


    @Test
    public void testRegisterRequestEmail() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("john.doe@example.com")
                .build();

        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
    }

    
    @Test
    public void testRegisterRequestPassword() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .password("password123")
                .build();

        Assertions.assertEquals("password123", registerRequest.getPassword());
    }


    @Test
    public void testRegisterRequestSetter() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        Assertions.assertEquals("John", registerRequest.getFirstname());
        Assertions.assertEquals("Doe", registerRequest.getLastname());
        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
        Assertions.assertEquals("password123", registerRequest.getPassword());
    }

    @Test
    public void testRegisterRequestEquals() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals(request1, request2);
    }

    @Test
    public void testRegisterRequestHashCode() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    }
    @Test
    public void testRegisterRequestToString() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        String expectedToString = "RegisterRequest(firstname=John, lastname=Doe, email=john.doe@example.com, password=password123)";
        Assertions.assertEquals(expectedToString, registerRequest.toString());
    }

    @Test
    public void testRegisterRequestNoArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest();

        Assertions.assertNull(registerRequest.getFirstname());
        Assertions.assertNull(registerRequest.getLastname());
        Assertions.assertNull(registerRequest.getEmail());
        Assertions.assertNull(registerRequest.getPassword());
    }

    @Test
    public void testRegisterRequestGetterSetter() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        Assertions.assertEquals("John", registerRequest.getFirstname());
        Assertions.assertEquals("Doe", registerRequest.getLastname());
        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
        Assertions.assertEquals("password123", registerRequest.getPassword());
    }
    @Test
    public void testRegisterRequestDataMethods() {
        // Create an initial RegisterRequest object
        RegisterRequest request1 = new RegisterRequest();
        request1.setFirstname("John");
        request1.setLastname("Doe");
        request1.setEmail("john.doe@example.com");
        request1.setPassword("password123");

        // Create a second RegisterRequest object with the same field values
        RegisterRequest request2 = new RegisterRequest();
        request2.setFirstname("John");
        request2.setLastname("Doe");
        request2.setEmail("john.doe@example.com");
        request2.setPassword("password123");

        // Test getters and setters
        Assertions.assertEquals("John", request1.getFirstname());
        Assertions.assertEquals("Doe", request1.getLastname());
        Assertions.assertEquals("john.doe@example.com", request1.getEmail());
        Assertions.assertEquals("password123", request1.getPassword());

        // Test equals method
        Assertions.assertEquals(request1, request2);

        // Test hashCode method
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());

        // Test toString method
        String expectedToString = "RegisterRequest(firstname=John, lastname=Doe, email=john.doe@example.com, password=password123)";
        Assertions.assertEquals(expectedToString, request1.toString());

        // Create a third RegisterRequest object with different field values
        RegisterRequest request3 = new RegisterRequest();
        request3.setFirstname("Jane");
        request3.setLastname("Smith");
        request3.setEmail("jane.smith@example.com");
        request3.setPassword("password123");

        // Test equals method with different object
        Assertions.assertNotEquals(request1, request3);

        // Test hashCode method with different object
        Assertions.assertNotEquals(request1.hashCode(), request3.hashCode());

        // Test toString method with different object
        String expectedToString2 = "RegisterRequest(firstname=Jane, lastname=Smith, email=jane.smith@example.com, password=password123)";
        Assertions.assertEquals(expectedToString2, request3.toString());
    }

    @Test
    public void testRegisterRequestSetterWithSameValue() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        // Set the same value again
        registerRequest.setFirstname("John");

        Assertions.assertEquals("John", registerRequest.getFirstname());
    }

    @Test
    public void testRegisterRequestEqualsWithDifferentClass() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        String name = "John";

        Assertions.assertNotEquals(registerRequest, name);
    }

    @Test
    public void testRegisterRequestHashCodeWithDifferentObject() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        String name = "John";

        Assertions.assertNotEquals(registerRequest.hashCode(), name.hashCode());
    }

    @Test
    public void testRegisterRequestToStringWithNullValues() {
        RegisterRequest registerRequest = new RegisterRequest();

        String expectedToString = "RegisterRequest(firstname=null, lastname=null, email=null, password=null)";
        Assertions.assertEquals(expectedToString, registerRequest.toString());
    }

    @Test
    public void testRegisterRequestNoArgsConstructorWithNonNullValues() {
        RegisterRequest registerRequest = new RegisterRequest();

        // Set non-null values
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        Assertions.assertEquals("John", registerRequest.getFirstname());
        Assertions.assertEquals("Doe", registerRequest.getLastname());
        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
        Assertions.assertEquals("password123", registerRequest.getPassword());
    }

    @Test
    public void testRegisterRequestEqualsWithSameObject() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        Assertions.assertEquals(registerRequest, registerRequest);
    }

    @Test
    public void testRegisterRequestHashCodeWithSameObject() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        Assertions.assertEquals(registerRequest.hashCode(), registerRequest.hashCode());
    }

    @Test
    public void testRegisterRequestEqualsWithNullObject() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");

        Assertions.assertNotEquals(registerRequest, null);
    }

    @Test
    public void testRegisterRequestEqualsWithDifferentFieldValues() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .password("password123")
                .build();

        Assertions.assertNotEquals(request1, request2);
    }

    @Test
    public void testRegisterRequestHashCodeWithDifferentFieldValues() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("Jane")
                .lastname("Smith")
                .email("jane.smith@example.com")
                .password("password123")
                .build();

        Assertions.assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testRegisterRequestToStringWithEmptyValues() {
        RegisterRequest registerRequest = new RegisterRequest();

        String expectedToString = "RegisterRequest(firstname=, lastname=, email=, password=)";
        String actualToString = registerRequest.toString().replaceAll("null", "");

        Assertions.assertEquals(expectedToString, actualToString);
    }


    @Test
    public void testInvalidFirstname() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("A");
        Assertions.assertThrows(ConstraintViolationException.class, () -> validate(registerRequest));
    }

    @Test
    public void testInvalidLastname() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setLastname("A");
        Assertions.assertThrows(ConstraintViolationException.class, () -> validate(registerRequest));
    }

    @Test
    public void testInvalidEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("invalid_email");
        Assertions.assertThrows(ConstraintViolationException.class, () -> validate(registerRequest));
    }

    @Test
    public void testInvalidPassword() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("ab");
        Assertions.assertThrows(ConstraintViolationException.class, () -> validate(registerRequest));
    }

    // Helper method to validate the RegisterRequest using javax.validation.Validator
    private void validate(RegisterRequest registerRequest) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
