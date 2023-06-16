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
}