package com.artconnect.backend.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


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
}