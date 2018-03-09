package sk.spacecode.spidersolitare.services;


import sk.spacecode.spidersolitare.entities.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;
    List<Score> getBestScores(String game) throws ScoreException;
}
