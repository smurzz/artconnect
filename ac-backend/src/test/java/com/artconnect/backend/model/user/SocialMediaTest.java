package com.artconnect.backend.model.user;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SocialMediaTest {

    @Test
    public void testSocialMedia() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setTitle("Twitter");
        socialMedia.setLink("https://twitter.com");

        // Verify the values
        Assertions.assertEquals("Twitter", socialMedia.getTitle());
        Assertions.assertEquals("https://twitter.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaBuilder() {
        // Create a SocialMedia object using the builder
        SocialMedia socialMedia = SocialMedia.builder()
                .title("Facebook")
                .link("https://facebook.com")
                .build();

        // Verify the values
        Assertions.assertEquals("Facebook", socialMedia.getTitle());
        Assertions.assertEquals("https://facebook.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaNoArgsConstructor() {
        // Create a SocialMedia object using the no-args constructor
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setTitle("Instagram");
        socialMedia.setLink("https://instagram.com");

        // Verify the values
        Assertions.assertEquals("Instagram", socialMedia.getTitle());
        Assertions.assertEquals("https://instagram.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaAllArgsConstructor() {
        // Create a SocialMedia object using the all-args constructor
        SocialMedia socialMedia = new SocialMedia("LinkedIn", "https://linkedin.com");

        // Verify the values
        Assertions.assertEquals("LinkedIn", socialMedia.getTitle());
        Assertions.assertEquals("https://linkedin.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaSetTitle() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia();

        // Set the title
        socialMedia.setTitle("YouTube");

        // Verify the title is set correctly
        Assertions.assertEquals("YouTube", socialMedia.getTitle());
    }

    @Test
    public void testSocialMediaSetLink() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia();

        // Set the link
        socialMedia.setLink("https://youtube.com");

        // Verify the link is set correctly
        Assertions.assertEquals("https://youtube.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaEquals() {
        // Create two SocialMedia objects with the same values
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the objects are equal
        Assertions.assertEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaNotEquals() {
        // Create two SocialMedia objects with different values
        SocialMedia socialMedia1 = new SocialMedia("Facebook", "https://facebook.com");
        SocialMedia socialMedia2 = new SocialMedia("Instagram", "https://instagram.com");

        // Verify the objects are not equal
        Assertions.assertNotEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaToString() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("LinkedIn", "https://linkedin.com");

        // Verify the toString() representation
        String expectedToString = "SocialMedia(title=LinkedIn, link=https://linkedin.com)";
        Assertions.assertEquals(expectedToString, socialMedia.toString());
    }

    @Test
    public void testSocialMediaBuilderWithDefaults() {
        // Create a SocialMedia object using the builder with default values
        SocialMedia socialMedia = SocialMedia.builder().build();

        // Verify the default values
        Assertions.assertNull(socialMedia.getTitle());
        Assertions.assertNull(socialMedia.getLink());
    }

    @Test
    public void testSocialMediaEqualsAndHashCode() {
        // Create two SocialMedia objects with the same values
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the equals() method
        Assertions.assertTrue(socialMedia1.equals(socialMedia2));
        Assertions.assertTrue(socialMedia2.equals(socialMedia1));

        // Verify the hashCode() method
        Assertions.assertEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaNotEqualsAndHashCode() {
        // Create two SocialMedia objects with different values
        SocialMedia socialMedia1 = new SocialMedia("Facebook", "https://facebook.com");
        SocialMedia socialMedia2 = new SocialMedia("LinkedIn", "https://linkedin.com");

        // Verify the equals() method
        Assertions.assertFalse(socialMedia1.equals(socialMedia2));
        Assertions.assertFalse(socialMedia2.equals(socialMedia1));

        // Verify the hashCode() method
        Assertions.assertNotEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaCopy() {
        // Create a SocialMedia object
        SocialMedia original = SocialMedia.builder()
                .title("Facebook")
                .link("https://facebook.com")
                .build();

        // Create a copy using the builder
        SocialMedia copy = SocialMedia.builder()
                .title(original.getTitle())
                .link(original.getLink())
                .build();

        // Verify that the copy has the same values as the original
        Assertions.assertEquals(original.getTitle(), copy.getTitle());
        Assertions.assertEquals(original.getLink(), copy.getLink());
    }

    @Test
    public void testSocialMediaSetterAndGetters() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia();

        // Set the title and link using setters
        socialMedia.setTitle("YouTube");
        socialMedia.setLink("https://youtube.com");

        // Verify the values using getters
        Assertions.assertEquals("YouTube", socialMedia.getTitle());
        Assertions.assertEquals("https://youtube.com", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaHashCode() {
        // Create two SocialMedia objects with the same values
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the hashCode() method
        Assertions.assertEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaToStringWithNullValues() {
        // Create a SocialMedia object with null values
        SocialMedia socialMedia = new SocialMedia(null, null);

        // Verify the toString() representation with null values
        String expectedToString = "SocialMedia(title=null, link=null)";
        Assertions.assertEquals(expectedToString, socialMedia.toString());
    }

    @Test
    public void testSocialMediaEqualsWithNull() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the equals() method with null
        Assertions.assertNotEquals(socialMedia, null);
    }

    @Test
    public void testSocialMediaEqualsWithDifferentObjectType() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the equals() method with a different object type
        Assertions.assertNotEquals(socialMedia, "Twitter");
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithDefaultValues() {
        // Create two SocialMedia objects with default values
        SocialMedia socialMedia1 = new SocialMedia();
        SocialMedia socialMedia2 = new SocialMedia();

        // Verify the equals() method with default values
        Assertions.assertEquals(socialMedia1, socialMedia2);

        // Verify the hashCode() method with default values
        Assertions.assertEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

}
