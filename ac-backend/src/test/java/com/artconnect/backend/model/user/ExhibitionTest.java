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
        Assertions.assertEquals(0, exhibition.getYear());
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

}
