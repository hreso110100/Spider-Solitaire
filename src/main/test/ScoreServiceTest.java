import org.junit.Test;
import sk.tuke.gamestudio.spidersolitaire.server.entity.Score;
import sk.tuke.gamestudio.spidersolitaire.server.service.ScoreService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreServiceTest {
    ScoreService scoreService;
    private final String GAME_NAME = "spider-solitaire";

    @Test
    public void testDbInit() throws Exception {
        assertEquals(0, scoreService.getBestScores(GAME_NAME).size());
    }

    @Test
    public void testAddScore() throws Exception {
        Score score = new Score("david", 1500, new Date());
        scoreService.addScore(score);
        assertEquals(1, scoreService.getBestScores(GAME_NAME).size());
    }

    @Test
    public void testGetBestScores() throws Exception {
        Score s1 = new Score("player1", 200, new Date());
        Score s2 = new Score("player2", 300, new Date());

        scoreService.addScore(s1);
        scoreService.addScore(s2);

        List<Score> scores = scoreService.getBestScores(GAME_NAME);
        assertEquals(s2.getPoints(), scores.get(0).getPoints());
        assertEquals(s2.getPlayer(), scores.get(0).getPlayer());
    }
}