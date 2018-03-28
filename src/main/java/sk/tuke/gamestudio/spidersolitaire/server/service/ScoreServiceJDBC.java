package sk.tuke.gamestudio.spidersolitaire.server.service;

import sk.tuke.gamestudio.spidersolitaire.server.entity.Score;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/*
    CREATE TABLE score (
        player VARCHAR(64) NOT NULL,
        game VARCHAR(64) NOT NULL,
        points INTEGER NOT NULL,
        playedon TIMESTAMP NOT NULL
    );
     */

/**
 * Service for score implemented via JDBC
 */

public class ScoreServiceJDBC implements ScoreService {
    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio?verifyServerCertificate=false&useSSL=true";
    private static final String USER = "root";
    private static final String PASSWORD = "benqfp7ig";

    private static final String INSERT_SCORE =
            "INSERT INTO score (player, game, points, playedon) VALUES (?, ?, ?, ?)";

    private static final String SELECT_SCORE =
            "SELECT player, game, points, playedon FROM score WHERE game = 'spider-solitaire' ORDER BY points DESC LIMIT 10;";

    /**
     * This method add score to database
     *
     * @param score score which will be saved to database
     * @throws ScoreException
     */

    @Override
    public void addScore(Score score) throws ScoreException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_SCORE)) {
                ps.setString(1, score.getPlayer());
                ps.setString(2, score.getGame());
                ps.setInt(3, score.getPoints());
                ps.setDate(4, new Date(score.getPlayedOn().getTime()));

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ScoreException("Error saving score", e);
        }
    }

    /**
     * This method returns list of scores
     *
     * @param game type of game
     * @return list of scores
     * @throws ScoreException
     */

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCORE)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Score score;
                        score = new Score(
                                resultSet.getString(1),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(4)
                        );
                        scores.add(score);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Error loading score", e);
        }
        return scores;
    }
}
