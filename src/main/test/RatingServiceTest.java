import org.junit.Test;
import sk.tuke.gamestudio.spidersolitaire.entities.Rating;
import sk.tuke.gamestudio.spidersolitaire.services.RatingService;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RatingServiceTest {
    RatingService ratingService;
    private final String GAME_NAME = "spider-solitaire";
    private final String TEST_PLAYER = "Tester";
    private final String TEST_PLAYER_2 = "Tester2";
    private final String TEST_PLAYER_3 = "Tester3";


    @Test
    public void testAddRating() throws Exception {
        Rating rating = new Rating(TEST_PLAYER, 5, new Date());
        ratingService.setRating(rating);
        assertEquals(rating.getRating(), ratingService.getRating(GAME_NAME, TEST_PLAYER));
    }

    @Test
    public void testGetRating() throws Exception {

        Rating rating1 = new Rating(TEST_PLAYER, 1, new Date());
        Rating rating2 = new Rating(TEST_PLAYER, 2, new Date());

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);

        assertEquals(rating2.getRating(), ratingService.getRating(GAME_NAME, TEST_PLAYER));
    }

    @Test
    public void testGetAverageRating() throws Exception {
        Rating rating1 = new Rating(TEST_PLAYER, 1, new Date());
        Rating rating2 = new Rating(TEST_PLAYER_2, 2, new Date());
        Rating rating3 = new Rating(TEST_PLAYER_3, 3, new Date());

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);
        ratingService.setRating(rating3);

        int result = ratingService.getAverageRating(GAME_NAME);

        assertEquals(2, result);
    }
}
