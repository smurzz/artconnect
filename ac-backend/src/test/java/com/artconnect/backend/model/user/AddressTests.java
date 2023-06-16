package com.artconnect.backend.model.user;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AddressTests {

    @Test
    public void testAddressFields() {
        // Arrange
        String street = "Main Street";
        String postalCode = "12345";
        String city = "New York";
        String country = "USA";

        // Act
        Address address = Address.builder()
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .country(country)
                .build();

        // Assert
        Assertions.assertEquals(street, address.getStreet());
        Assertions.assertEquals(postalCode, address.getPostalCode());
        Assertions.assertEquals(city, address.getCity());
        Assertions.assertEquals(country, address.getCountry());
    }

    @Test
    public void testAddressConstructor() {
        // Arrange
        String street = "Main Street";
        String postalCode = "12345";
        String city = "New York";
        String country = "USA";

        // Act
        Address address = new Address(street, postalCode, city, country);

        // Assert
        Assertions.assertEquals(street, address.getStreet());
        Assertions.assertEquals(postalCode, address.getPostalCode());
        Assertions.assertEquals(city, address.getCity());
        Assertions.assertEquals(country, address.getCountry());
    }

    @Test
    public void testAddressEquality() {
        // Arrange
        Address address1 = Address.builder()
                .street("Main Street")
                .postalCode("12345")
                .city("New York")
                .country("USA")
                .build();

        Address address2 = Address.builder()
                .street("Main Street")
                .postalCode("12345")
                .city("New York")
                .country("USA")
                .build();

        Address address3 = Address.builder()
                .street("Second Street")
                .postalCode("54321")
                .city("Los Angeles")
                .country("USA")
                .build();

        // Assert
        Assertions.assertEquals(address1, address2); // Same field values, so should be equal
        Assertions.assertNotEquals(address1, address3); // Different field values, so should not be equal
    }

    @Test
    public void testAddressBuilderWithDefaultValues() {
        // Arrange & Act
        Address address = Address.builder().build();

        // Assert
        Assertions.assertNull(address.getStreet());
        Assertions.assertNull(address.getPostalCode());
        Assertions.assertNull(address.getCity());
        Assertions.assertNull(address.getCountry());
    }

    @Test
    public void testAddressBuilderWithUpdatedValues() {
        // Arrange
        String initialStreet = "Main Street";
        String initialPostalCode = "12345";
        String initialCity = "New York";
        String initialCountry = "USA";

        String updatedStreet = "Second Street";
        String updatedPostalCode = "54321";
        String updatedCity = "Los Angeles";
        String updatedCountry = "USA";

        // Act
        Address address = Address.builder()
                .street(initialStreet)
                .postalCode(initialPostalCode)
                .city(initialCity)
                .country(initialCountry)
                .build();

        address = Address.builder()
                .street(updatedStreet)
                .postalCode(updatedPostalCode)
                .city(updatedCity)
                .country(updatedCountry)
                .build();

        // Assert
        Assertions.assertEquals(updatedStreet, address.getStreet());
        Assertions.assertEquals(updatedPostalCode, address.getPostalCode());
        Assertions.assertEquals(updatedCity, address.getCity());
        Assertions.assertEquals(updatedCountry, address.getCountry());
    }

    @Test
    public void testAddressBuilderCopy() {
        // Arrange
        String street = "Main Street";
        String postalCode = "12345";
        String city = "New York";
        String country = "USA";

        Address address = Address.builder()
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .country(country)
                .build();

        // Act
        Address copiedAddress = Address.builder()
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();

        // Assert
        Assertions.assertEquals(address, copiedAddress);
    }

    @Test
    public void testAddressHashCode() {
        // Arrange
        Address address1 = Address.builder()
                .street("Main Street")
                .postalCode("12345")
                .city("New York")
                .country("USA")
                .build();

        Address address2 = Address.builder()
                .street("Main Street")
                .postalCode("12345")
                .city("New York")
                .country("USA")
                .build();

        // Assert
        Assertions.assertEquals(address1.hashCode(), address2.hashCode()); // Same field values, so hashCodes should be equal
    }

    @Test
    public void testNoArgsConstructor() {
        // Act
        Address address = new Address();

        // Assert
        Assertions.assertNull(address.getStreet());
        Assertions.assertNull(address.getPostalCode());
        Assertions.assertNull(address.getCity());
        Assertions.assertNull(address.getCountry());
    }

    @Test
    public void testAddressSetterMethods() {
        // Arrange
        Address address = new Address();

        // Act
        address.setStreet("Main Street");
        address.setPostalCode("12345");
        address.setCity("New York");
        address.setCountry("USA");

        // Assert
        Assertions.assertEquals("Main Street", address.getStreet());
        Assertions.assertEquals("12345", address.getPostalCode());
        Assertions.assertEquals("New York", address.getCity());
        Assertions.assertEquals("USA", address.getCountry());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String street = "Main Street";
        String postalCode = "12345";
        String city = "New York";
        String country = "USA";

        // Act
        Address address = new Address(street, postalCode, city, country);

        // Assert
        Assertions.assertEquals(street, address.getStreet());
        Assertions.assertEquals(postalCode, address.getPostalCode());
        Assertions.assertEquals(city, address.getCity());
        Assertions.assertEquals(country, address.getCountry());
    }

    @Test
    public void testSetterMethods() {
        // Arrange
        Address address = new Address();

        // Act
        address.setStreet("Main Street");
        address.setPostalCode("12345");
        address.setCity("New York");
        address.setCountry("USA");

        // Assert
        Assertions.assertEquals("Main Street", address.getStreet());
        Assertions.assertEquals("12345", address.getPostalCode());
        Assertions.assertEquals("New York", address.getCity());
        Assertions.assertEquals("USA", address.getCountry());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        Address address1 = new Address("Main Street", "12345", "New York", "USA");
        Address address2 = new Address("Main Street", "12345", "New York", "USA");
        Address address3 = new Address("Second Street", "54321", "Los Angeles", "USA");

        // Assert
        Assertions.assertEquals(address1, address2); // Same field values, so should be equal
        Assertions.assertNotEquals(address1, address3); // Different field values, so should not be equal

        Assertions.assertEquals(address1.hashCode(), address2.hashCode()); // Same field values, so hashCodes should be equal
    }

    @Test
    public void testToString() {
        // Arrange
        Address address = new Address("Main Street", "12345", "New York", "USA");

        // Act
        String addressString = address.toString();

        // Assert
        Assertions.assertTrue(addressString.contains("street=Main Street"));
        Assertions.assertTrue(addressString.contains("postalCode=12345"));
        Assertions.assertTrue(addressString.contains("city=New York"));
        Assertions.assertTrue(addressString.contains("country=USA"));
    }

    @Test
    public void testBuilderPattern() {
        // Arrange
        String street = "Main Street";
        String postalCode = "12345";
        String city = "New York";
        String country = "USA";

        // Act
        Address address = Address.builder()
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .country(country)
                .build();

        // Assert
        Assertions.assertEquals(street, address.getStreet());
        Assertions.assertEquals(postalCode, address.getPostalCode());
        Assertions.assertEquals(city, address.getCity());
        Assertions.assertEquals(country, address.getCountry());
    }
}