package cs410.webfilmz;

/*
 *
 * ADD YOUR User TESTS TO THIS FILE
 *
 */

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {


    // Test the genre recommendations
    @Test
    void getRecommendationsByGenre() {
        // Check if unwatched films from the liked genre(s) are returned
        Catalog catalog = CatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        // Romance films without watched - {Titanic, The Princess Bride}
        assertEquals(
                Set.of(catalog.findByTitle("Titanic"), catalog.findByTitle("The Princess Bride")),
                alice.getRecommendationsByGenre(catalog));
        Film terminator = catalog.findByTitle("The Terminator");
        alice.addWatched(terminator);
        alice.addLiked(terminator);
    }

    // Test for getAllRecommendations new releases, favorite directors and favorite genres
    @Test
    void getAllRecommendations() {
        Catalog catalog = CatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);

        // Check new releases - {Oppenheimer, The Martian, Inception}
        assertEquals(
                Set.of(catalog.findByTitle("Oppenheimer"), catalog.findByTitle("The Martian"),
                        catalog.findByTitle("Inception")),
                alice.getAllRecommendations(catalog, 3).get("New Releases"));

        // Check by directors - {The City of Lost Children}
        assertEquals(
                Set.of(catalog.findByTitle("The City of Lost Children")),
                alice.getAllRecommendations(catalog, 3).get("Favorite Directors"));

        // Check by genre - {Titanic, The Princess Bride}
        assertEquals(
                Set.of(catalog.findByTitle("Titanic"), catalog.findByTitle("The Princess Bride")),
                alice.getAllRecommendations(catalog, 3).get("Favorite Genres"));
    }

    // Check liked director
    @Test
    void isLikedDirector() {
        Catalog catalog = CatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedDirector("Jean-Pierre Jeunet"));
        assertFalse(alice.isLikedDirector("Ridley Scott"));
        Film oppenheimer = catalog.findByTitle("Oppenheimer");
        alice.addWatched(oppenheimer);
        alice.addLiked(oppenheimer);
        assertTrue(alice.isLikedDirector("Christopher Nolan"));
        assertFalse(alice.isLikedDirector("James Cameron"));
    }

    // Check liked genre
    @Test
    void isLikedGenre() {
        Catalog catalog = CatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedGenre("Romance"));
        assertFalse(alice.isLikedGenre("SciFi"));
        Film oppenheimer = catalog.findByTitle("Oppenheimer");
        alice.addWatched(oppenheimer);
        alice.addLiked(oppenheimer);
        assertTrue(alice.isLikedGenre("Bio"));
        assertFalse(alice.isLikedGenre("Thriller"));
    }
}

