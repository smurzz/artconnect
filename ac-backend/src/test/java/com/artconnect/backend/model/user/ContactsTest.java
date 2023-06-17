package com.artconnect.backend.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ContactsTest {
    private Contacts contacts;

    @Mock
    private Address address;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create an instance of Address
        address = Address.builder()
                .street("123 Main St")
                .postalCode("12345")
                .city("City")
                .country("Country")
                .build();

        // Create an instance of Contacts
        contacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();
    }

    @Test
    public void testGetTelefonNumber() {
        int telefonNumber = contacts.getTelefonNumber();
        assertEquals(123456789, telefonNumber);
    }

    @Test
    public void testGetAddress() {
        Address expectedAddress = Address.builder()
                .street("123 Main St")
                .postalCode("12345")
                .city("City")
                .country("Country")
                .build();
        Address actualAddress = contacts.getAddress();
        Assertions.assertEquals(expectedAddress, actualAddress);
    }

    @Test
    public void testGetWebsite() {
        String website = contacts.getWebsite();
        assertEquals("www.example.com", website);
    }

    @Test
    public void testSetTelefonNumber() {
        contacts.setTelefonNumber(987654321);
        assertEquals(987654321, contacts.getTelefonNumber());
    }

    @Test
    public void testSetAddress() {
        Address newAddress = Address.builder()
                .street("456 Elm St")
                .postalCode("54321")
                .city("New City")
                .country("New Country")
                .build();
        contacts.setAddress(newAddress);
        assertEquals(newAddress, contacts.getAddress());
    }

    @Test
    public void testSetWebsite() {
        contacts.setWebsite("www.new-website.com");
        assertEquals("www.new-website.com", contacts.getWebsite());
    }

    @Test
    public void testContacts() {
        // Create an instance of Address
        Address address = Address.builder()
                .street("123 Main St")
                .postalCode("12345")
                .city("City")
                .country("Country")
                .build();

        // Create an instance of Contacts
        Contacts contacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();

        // Verify the values
        assertEquals(123456789, contacts.getTelefonNumber());
        assertEquals(address, contacts.getAddress());
        assertEquals("www.example.com", contacts.getWebsite());
    }

    @Test
    public void testNoArgsConstructor() {
        Contacts emptyContacts = new Contacts();
        assertEquals(0, emptyContacts.getTelefonNumber());
        assertEquals(null, emptyContacts.getAddress());
        assertEquals(null, emptyContacts.getWebsite());
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance of Address
        Address address = Address.builder()
                .street("123 Main St")
                .postalCode("12345")
                .city("City")
                .country("Country")
                .build();

        // Create an instance of Contacts
        Contacts allArgsContacts = new Contacts(123456789, address, "www.example.com");

        // Verify the values
        assertEquals(123456789, allArgsContacts.getTelefonNumber());
        assertEquals(address, allArgsContacts.getAddress());
        assertEquals("www.example.com", allArgsContacts.getWebsite());
    }

    @Test
    public void testGettersAndSetters() {
        // Test getters
        assertEquals(123456789, contacts.getTelefonNumber());
        assertEquals(address, contacts.getAddress());
        assertEquals("www.example.com", contacts.getWebsite());

        // Test setters
        contacts.setTelefonNumber(987654321);
        contacts.setAddress(null);
        contacts.setWebsite("www.new-website.com");

        assertEquals(987654321, contacts.getTelefonNumber());
        assertEquals(null, contacts.getAddress());
        assertEquals("www.new-website.com", contacts.getWebsite());
    }

    @Test
    public void testEqualsAndHashCode() {
        Contacts contacts1 = new Contacts(123456789, address, "www.example.com");
        Contacts contacts2 = new Contacts(123456789, address, "www.example.com");
        Contacts contacts3 = new Contacts(987654321, null, "www.new-website.com");

        // Test equals
        assertEquals(contacts1, contacts2);
        assertNotEquals(contacts1, contacts3);

        // Test hashCode
        assertEquals(contacts1.hashCode(), contacts2.hashCode());
        assertNotEquals(contacts1.hashCode(), contacts3.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Contacts(telefonNumber=123456789, address=Address(street=123 Main St, postalCode=12345, city=City, country=Country), website=www.example.com)";
        assertEquals(expectedString, contacts.toString());
    }

    @Test
    public void testBuilder() {
        // Create an instance using the builder
        Contacts builderContacts = Contacts.builder()
                .telefonNumber(987654321)
                .address(null)
                .website("www.test.com")
                .build();

        // Verify the values
        assertEquals(987654321, builderContacts.getTelefonNumber());
        assertEquals(null, builderContacts.getAddress());
        assertEquals("www.test.com", builderContacts.getWebsite());
    }

    @Test
    public void testAllArgsConstructorWithDefaults() {
        // Create an instance using the all-args constructor with default values
        Contacts allArgsWithDefaultsContacts = new Contacts();

        // Verify the default values
        assertEquals(0, allArgsWithDefaultsContacts.getTelefonNumber());
        assertEquals(null, allArgsWithDefaultsContacts.getAddress());
        assertEquals(null, allArgsWithDefaultsContacts.getWebsite());
    }

    @Test
    public void testNoArgsConstructorWithDefaults() {
        // Create an instance using the no-args constructor with default values
        Contacts noArgsConstructorContacts = new Contacts();

        // Verify the default values
        assertEquals(0, noArgsConstructorContacts.getTelefonNumber());
        assertEquals(null, noArgsConstructorContacts.getAddress());
        assertEquals(null, noArgsConstructorContacts.getWebsite());
    }

    @Test
    public void testSetterAndGettersWithNonNullValues() {
        // Create new values
        Address newAddress = Address.builder()
                .street("789 Elm St")
                .postalCode("54321")
                .city("New City")
                .country("New Country")
                .build();
        String newWebsite = "www.new-website.com";

        // Set new values using setters
        contacts.setTelefonNumber(987654321);
        contacts.setAddress(newAddress);
        contacts.setWebsite(newWebsite);

        // Verify the updated values using getters
        assertEquals(987654321, contacts.getTelefonNumber());
        assertEquals(newAddress, contacts.getAddress());
        assertEquals(newWebsite, contacts.getWebsite());
    }

    @Test
    public void testSetterAndGettersWithNullValues() {
        // Set null values using setters
        contacts.setTelefonNumber(0);
        contacts.setAddress(null);
        contacts.setWebsite(null);

        // Verify the updated null values using getters
        assertEquals(0, contacts.getTelefonNumber());
        assertEquals(null, contacts.getAddress());
        assertEquals(null, contacts.getWebsite());
    }

    @Test
    public void testEqualsAndHashCodeWithSameInstance() {
        // The contacts object should be equal to itself and have the same hash code
        assertEquals(contacts, contacts);
        assertEquals(contacts.hashCode(), contacts.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithEqualObjects() {
        // Create a new Contacts object with the same values
        Contacts sameContacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();

        // The contacts objects should be equal and have the same hash code
        assertEquals(contacts, sameContacts);
        assertEquals(contacts.hashCode(), sameContacts.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTelefonNumber() {
        // Create a new Contacts object with a different telefonNumber
        Contacts differentContacts = Contacts.builder()
                .telefonNumber(987654321)
                .address(address)
                .website("www.example.com")
                .build();

        // The contacts objects should not be equal and have different hash codes
        assertNotEquals(contacts, differentContacts);
        assertNotEquals(contacts.hashCode(), differentContacts.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentAddress() {
        // Create a new Address object with different values
        Address differentAddress = Address.builder()
                .street("456 Elm St")
                .postalCode("54321")
                .city("New City")
                .country("New Country")
                .build();

        // Create a new Contacts object with a different address
        Contacts differentContacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(differentAddress)
                .website("www.example.com")
                .build();

        // The contacts objects should not be equal and have different hash codes
        assertNotEquals(contacts, differentContacts);
        assertNotEquals(contacts.hashCode(), differentContacts.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentWebsite() {
        // Create a new Contacts object with a different website
        Contacts differentContacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.new-website.com")
                .build();

        // The contacts objects should not be equal and have different hash codes
        assertNotEquals(contacts, differentContacts);
        assertNotEquals(contacts.hashCode(), differentContacts.hashCode());
    }

    @Test
    public void testAllArgsConstructorWithNullValues() {
        // Create an instance of Contacts with null values
        Contacts allArgsNullContacts = new Contacts(0, null, null);

        // Verify the null values
        assertEquals(0, allArgsNullContacts.getTelefonNumber());
        assertEquals(null, allArgsNullContacts.getAddress());
        assertEquals(null, allArgsNullContacts.getWebsite());
    }

    @Test
    public void testAllArgsConstructorWithNonNullValues() {
        // Create an instance of Contacts with non-null values
        Contacts allArgsNonNullContacts = new Contacts(987654321, address, "www.new-website.com");

        // Verify the non-null values
        assertEquals(987654321, allArgsNonNullContacts.getTelefonNumber());
        assertEquals(address, allArgsNonNullContacts.getAddress());
        assertEquals("www.new-website.com", allArgsNonNullContacts.getWebsite());
    }

    @Test
    public void testNoArgsConstructorEqualsAndHashCode() {
        // Create two instances using the no-args constructor
        Contacts noArgsConstructorContacts1 = new Contacts();
        Contacts noArgsConstructorContacts2 = new Contacts();

        // The contacts objects created with the no-args constructor should be equal and have the same hash code
        assertEquals(noArgsConstructorContacts1, noArgsConstructorContacts2);
        assertEquals(noArgsConstructorContacts1.hashCode(), noArgsConstructorContacts2.hashCode());
    }

    @Test
    public void testAllArgsConstructorEqualsAndHashCode() {
        // Create two instances using the all-args constructor with the same values
        Contacts allArgsConstructorContacts1 = new Contacts(123456789, address, "www.example.com");
        Contacts allArgsConstructorContacts2 = new Contacts(123456789, address, "www.example.com");

        // The contacts objects created with the all-args constructor should be equal and have the same hash code
        assertEquals(allArgsConstructorContacts1, allArgsConstructorContacts2);
        assertEquals(allArgsConstructorContacts1.hashCode(), allArgsConstructorContacts2.hashCode());
    }

    @Test
    public void testNoArgsConstructorWithDefaultValuesEqualsAndHashCode() {
        // Create an instance using the no-args constructor with default values
        Contacts noArgsConstructorWithDefaultsContacts = new Contacts();

        // Create an instance using the all-args constructor with default values
        Contacts allArgsConstructorWithDefaultsContacts = new Contacts(0, null, null);

        // The contacts objects created with the no-args constructor and the all-args constructor with default values should be equal and have the same hash code
        assertEquals(noArgsConstructorWithDefaultsContacts, allArgsConstructorWithDefaultsContacts);
        assertEquals(noArgsConstructorWithDefaultsContacts.hashCode(), allArgsConstructorWithDefaultsContacts.hashCode());
    }

    @Test
    public void testAllArgsConstructorWithDifferentValues() {
        // Create two instances using the all-args constructor with different values
        Contacts allArgsConstructorContacts1 = new Contacts(123456789, address, "www.example.com");
        Contacts allArgsConstructorContacts2 = new Contacts(987654321, null, "www.new-website.com");

        // The contacts objects created with different values should not be equal
        assertNotEquals(allArgsConstructorContacts1, allArgsConstructorContacts2);
    }

    @Test
    public void testBuilderEqualsAndHashCode() {
        // Create two instances using the builder with the same values
        Contacts builderContacts1 = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();
        Contacts builderContacts2 = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();

        // The contacts objects created with the builder should be equal and have the same hash code
        assertEquals(builderContacts1, builderContacts2);
        assertEquals(builderContacts1.hashCode(), builderContacts2.hashCode());
    }

    @Test
    public void testBuilderWithDifferentValues() {
        // Create two instances using the builder with different values
        Contacts builderContacts1 = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("www.example.com")
                .build();
        Contacts builderContacts2 = Contacts.builder()
                .telefonNumber(987654321)
                .address(null)
                .website("www.new-website.com")
                .build();

        // The contacts objects created with different values should not be equal
        assertNotEquals(builderContacts1, builderContacts2);
    }

}