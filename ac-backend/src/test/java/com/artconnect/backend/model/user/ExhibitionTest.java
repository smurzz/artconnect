package com.artconnect.backend.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExhibitionTest {

    @Test
    public void testExhibitionConstructorAndGetters() {
        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        Exhibition exhibition = new Exhibition(title, location, year, description);

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionSetters() {
        Exhibition exhibition = new Exhibition();

        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        exhibition.setTitle(title);
        exhibition.setLocation(location);
        exhibition.setYear(year);
        exhibition.setDescription(description);

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionBuilder() {
        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .location(location)
                .year(year)
                .description(description)
                .build();

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionEqualsAndHashCode() {
        Exhibition exhibition1 = Exhibition.builder()
                .title("Art Exhibition")
                .location("Gallery")
                .year(2022)
                .description("A showcase of contemporary art")
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .title("Art Exhibition")
                .location("Gallery")
                .year(2022)
                .description("A showcase of contemporary art")
                .build();

        Exhibition exhibition3 = Exhibition.builder()
                .title("Sculpture Exhibition")
                .location("Museum")
                .year(2023)
                .description("An exhibition of modern sculptures")
                .build();

        // Verify equals() method
        Assertions.assertEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1, exhibition3);

        // Verify hashCode() method
        Assertions.assertEquals(exhibition1.hashCode(), exhibition2.hashCode());
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition3.hashCode());
    }

    @Test
    public void testExhibitionToString() {
        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .location(location)
                .year(year)
                .description(description)
                .build();

        String expectedToString = "Exhibition(title=Art Exhibition, location=Gallery, year=2022, description=A showcase of contemporary art)";
        Assertions.assertEquals(expectedToString, exhibition.toString());
    }

    @Test
    public void testExhibitionWithEmptyConstructor() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertNull(exhibition.getTitle());
        Assertions.assertNull(exhibition.getLocation());
        Assertions.assertNull(exhibition.getYear());
        Assertions.assertNull(exhibition.getDescription());
    }

    @Test
    public void testExhibitionWithPartialData() {
        String title = "Art Exhibition";
        int year = 2022;

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .year(year)
                .build();

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertNull(exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertNull(exhibition.getDescription());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithPartialData() {
        Exhibition exhibition1 = Exhibition.builder()
                .title("Art Exhibition")
                .year(2022)
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .title("Art Exhibition")
                .year(2022)
                .build();

        Exhibition exhibition3 = Exhibition.builder()
                .title("Sculpture Exhibition")
                .year(2023)
                .build();

        // Verify equals() method
        Assertions.assertEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1, exhibition3);

        // Verify hashCode() method
        Assertions.assertEquals(exhibition1.hashCode(), exhibition2.hashCode());
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition3.hashCode());
    }

    @Test
    public void testExhibitionToStringWithPartialData() {
        String title = "Art Exhibition";
        int year = 2022;

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .year(year)
                .build();

        String expectedToString = "Exhibition(title=Art Exhibition, location=null, year=2022, description=null)";
        Assertions.assertEquals(expectedToString, exhibition.toString());
    }

    @Test
    public void testExhibitionConstructorWithNegativeYear() {
        String title = "Art Exhibition";
        String location = "Gallery";
        int year = -2022;
        String description = "A showcase of contemporary art";

        Exhibition exhibition = new Exhibition(title, location, year, description);

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionSettersWithNullValues() {
        Exhibition exhibition = new Exhibition();

        String title = null;
        String location = null;
        int year = 0;
        String description = null;

        exhibition.setTitle(title);
        exhibition.setLocation(location);
        exhibition.setYear(year);
        exhibition.setDescription(description);

        Assertions.assertNull(exhibition.getTitle());
        Assertions.assertNull(exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertNull(exhibition.getDescription());
    }

    @Test
    public void testExhibitionBuilderWithNullValues() {
        String title = null;
        String location = null;
        int year = 0;
        String description = null;

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .location(location)
                .year(year)
                .description(description)
                .build();

        Assertions.assertNull(exhibition.getTitle());
        Assertions.assertNull(exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertNull(exhibition.getDescription());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithNullValues() {
        Exhibition exhibition1 = Exhibition.builder()
                .title(null)
                .location(null)
                .year(0)
                .description(null)
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .title(null)
                .location(null)
                .year(0)
                .description(null)
                .build();

        // Verify equals() method
        Assertions.assertEquals(exhibition1, exhibition2);

        // Verify hashCode() method
        Assertions.assertEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionToStringWithNullValues() {
        String title = null;
        String location = null;
        int year = 0;
        String description = null;

        Exhibition exhibition = Exhibition.builder()
                .title(title)
                .location(location)
                .year(year)
                .description(description)
                .build();

        String expectedToString = "Exhibition(title=null, location=null, year=0, description=null)";
        Assertions.assertEquals(expectedToString, exhibition.toString());
    }

    @Test
    public void testExhibitionToStringWithEmptyValues() {
        Exhibition exhibition = Exhibition.builder().build();

        String expectedToString = "Exhibition(title=null, location=null, year=null, description=null)";
        Assertions.assertEquals(expectedToString, exhibition.toString());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithEmptyValues() {
        Exhibition exhibition1 = Exhibition.builder().build();
        Exhibition exhibition2 = Exhibition.builder().build();

        // Verify equals() method
        Assertions.assertEquals(exhibition1, exhibition2);

        // Verify hashCode() method
        Assertions.assertEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionNoArgsConstructor() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertNull(exhibition.getTitle());
        Assertions.assertNull(exhibition.getLocation());
        Assertions.assertNull(exhibition.getYear());
        Assertions.assertNull(exhibition.getDescription());
    }

    @Test
    public void testExhibitionAllArgsConstructor() {
        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        Exhibition exhibition = new Exhibition(title, location, year, description);

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionGetterAndSetter() {
        Exhibition exhibition = new Exhibition();

        String title = "Art Exhibition";
        String location = "Gallery";
        int year = 2022;
        String description = "A showcase of contemporary art";

        exhibition.setTitle(title);
        exhibition.setLocation(location);
        exhibition.setYear(year);
        exhibition.setDescription(description);

        Assertions.assertEquals(title, exhibition.getTitle());
        Assertions.assertEquals(location, exhibition.getLocation());
        Assertions.assertEquals(year, exhibition.getYear());
        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionSetTitle() {
        String title = "Art Exhibition";

        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(title);

        Assertions.assertEquals(title, exhibition.getTitle());
    }

    @Test
    public void testExhibitionSetLocation() {
        String location = "Gallery";

        Exhibition exhibition = new Exhibition();
        exhibition.setLocation(location);

        Assertions.assertEquals(location, exhibition.getLocation());
    }

    @Test
    public void testExhibitionSetYear() {
        int year = 2022;

        Exhibition exhibition = new Exhibition();
        exhibition.setYear(year);

        Assertions.assertEquals(year, exhibition.getYear());
    }

    @Test
    public void testExhibitionSetDescription() {
        String description = "A showcase of contemporary art";

        Exhibition exhibition = new Exhibition();
        exhibition.setDescription(description);

        Assertions.assertEquals(description, exhibition.getDescription());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithDifferentObject() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertNotEquals(exhibition, new Object());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithNullObject() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertNotEquals(exhibition, null);
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithSameObject() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertEquals(exhibition, exhibition);
        Assertions.assertEquals(exhibition.hashCode(), exhibition.hashCode());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithEqualObject() {
        Exhibition exhibition1 = new Exhibition();
        Exhibition exhibition2 = new Exhibition();

        Assertions.assertEquals(exhibition1, exhibition2);
        Assertions.assertEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithDifferentTitle() {
        Exhibition exhibition1 = Exhibition.builder()
                .title("Art Exhibition")
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .title("Sculpture Exhibition")
                .build();

        Assertions.assertNotEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithDifferentLocation() {
        Exhibition exhibition1 = Exhibition.builder()
                .location("Gallery A")
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .location("Gallery B")
                .build();

        Assertions.assertNotEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithDifferentYear() {
        Exhibition exhibition1 = Exhibition.builder()
                .year(2022)
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .year(2023)
                .build();

        Assertions.assertNotEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionEqualsAndHashCodeWithDifferentDescription() {
        Exhibition exhibition1 = Exhibition.builder()
                .description("Art exhibition showcasing paintings")
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .description("Art exhibition showcasing sculptures")
                .build();

        Assertions.assertNotEquals(exhibition1, exhibition2);
        Assertions.assertNotEquals(exhibition1.hashCode(), exhibition2.hashCode());
    }

    @Test
    public void testExhibitionCanEqualWithEqualObject() {
        Exhibition exhibition1 = Exhibition.builder()
                .title("Art Exhibition")
                .build();

        Exhibition exhibition2 = Exhibition.builder()
                .title("Art Exhibition")
                .build();

        Assertions.assertTrue(exhibition1.canEqual(exhibition2));
    }

    @Test
    public void testExhibitionCanEqualWithDifferentObject() {
        Exhibition exhibition = new Exhibition();

        Assertions.assertFalse(exhibition.canEqual(new Object()));
    }

}

