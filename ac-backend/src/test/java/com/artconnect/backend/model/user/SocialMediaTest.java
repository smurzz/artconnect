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

    @Test
    public void testSocialMediaSetTitleAndLink() {
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
    public void testSocialMediaEqualsAndHashCodeWithNullValues() {
        // Create two SocialMedia objects with null values
        SocialMedia socialMedia1 = new SocialMedia(null, null);
        SocialMedia socialMedia2 = new SocialMedia(null, null);

        // Verify the equals() method with null values
        Assertions.assertEquals(socialMedia1, socialMedia2);

        // Verify the hashCode() method with null values
        Assertions.assertEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithEmptyValues() {
        // Create two SocialMedia objects with empty values
        SocialMedia socialMedia1 = new SocialMedia("", "");
        SocialMedia socialMedia2 = new SocialMedia("", "");

        // Verify the equals() method with empty values
        Assertions.assertEquals(socialMedia1, socialMedia2);

        // Verify the hashCode() method with empty values
        Assertions.assertEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaNotEqualsWithDifferentTitle() {
        // Create two SocialMedia objects with different titles
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Facebook", "https://facebook.com");

        // Verify the objects are not equal
        Assertions.assertNotEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaNotEqualsWithDifferentLink() {
        // Create two SocialMedia objects with different links
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://facebook.com");

        // Verify the objects are not equal
        Assertions.assertNotEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaNotEqualsWithNullTitle() {
        // Create two SocialMedia objects with null title
        SocialMedia socialMedia1 = new SocialMedia(null, "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the objects are not equal
        Assertions.assertNotEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaNotEqualsWithNullLink() {
        // Create two SocialMedia objects with null link
        SocialMedia socialMedia1 = new SocialMedia("Twitter", null);
        SocialMedia socialMedia2 = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the objects are not equal
        Assertions.assertNotEquals(socialMedia1, socialMedia2);
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithSameObject() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the equals() method with the same object
        Assertions.assertEquals(socialMedia, socialMedia);

        // Verify the hashCode() method with the same object
        Assertions.assertEquals(socialMedia.hashCode(), socialMedia.hashCode());
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithDifferentObjectClass() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Verify the equals() method with a different object class
        Assertions.assertNotEquals(socialMedia, new Object());

        // Verify the hashCode() method with a different object class
        Assertions.assertNotEquals(socialMedia.hashCode(), new Object().hashCode());
    }

    @Test
    public void testSocialMediaToStringWithNonNullValues() {
        // Create a SocialMedia object with non-null values
        SocialMedia socialMedia = new SocialMedia("LinkedIn", "https://linkedin.com");

        // Verify the toString() representation with non-null values
        String expectedToString = "SocialMedia(title=LinkedIn, link=https://linkedin.com)";
        Assertions.assertEquals(expectedToString, socialMedia.toString());
    }

    @Test
    public void testSocialMediaSetTitleToNull() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Set the title to null
        socialMedia.setTitle(null);

        // Verify that the title is null
        Assertions.assertNull(socialMedia.getTitle());
    }

    @Test
    public void testSocialMediaSetLinkToNull() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Set the link to null
        socialMedia.setLink(null);

        // Verify that the link is null
        Assertions.assertNull(socialMedia.getLink());
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithDifferentObjects() {
        // Create two SocialMedia objects with different values
        SocialMedia socialMedia1 = new SocialMedia("Twitter", "https://twitter.com");
        SocialMedia socialMedia2 = new SocialMedia("Facebook", "https://facebook.com");

        // Verify the equals() method with different objects
        Assertions.assertNotEquals(socialMedia1, socialMedia2);

        // Verify the hashCode() method with different objects
        Assertions.assertNotEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }

    @Test
    public void testSocialMediaToStringWithEmptyValues() {
        // Create a SocialMedia object with empty values
        SocialMedia socialMedia = new SocialMedia("", "");

        // Verify the toString() representation with empty values
        String expectedToString = "SocialMedia(title=, link=)";
        Assertions.assertEquals(expectedToString, socialMedia.toString());
    }

    @Test
    public void testSocialMediaSetTitleAndLinkToEmpty() {
        // Create a SocialMedia object
        SocialMedia socialMedia = new SocialMedia("Twitter", "https://twitter.com");

        // Set the title and link to empty
        socialMedia.setTitle("");
        socialMedia.setLink("");

        // Verify that the title and link are empty
        Assertions.assertEquals("", socialMedia.getTitle());
        Assertions.assertEquals("", socialMedia.getLink());
    }

    @Test
    public void testSocialMediaEqualsAndHashCodeWithNullAndEmptyValues() {
        // Create two SocialMedia objects with null and empty values
        SocialMedia socialMedia1 = new SocialMedia(null, null);
        SocialMedia socialMedia2 = new SocialMedia("", "");

        // Verify the equals() method with null and empty values
        Assertions.assertNotEquals(socialMedia1, socialMedia2);

        // Verify the hashCode() method with null and empty values
        Assertions.assertNotEquals(socialMedia1.hashCode(), socialMedia2.hashCode());
    }
}
