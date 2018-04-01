package sk.tuke.gamestudio.server.service;


import sk.tuke.gamestudio.server.entity.Score;

import java.util.List;

/**
 * Interface for score to add and get score
 */

public interface ScoreService {
    void addScore(Score score) throws ScoreException;

    List<Score> getBestScores(String game) throws ScoreException;
}
