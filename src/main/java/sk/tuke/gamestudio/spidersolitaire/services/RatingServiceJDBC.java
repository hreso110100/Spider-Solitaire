package sk.tuke.gamestudio.spidersolitaire.services;

import sk.tuke.gamestudio.spidersolitaire.entities.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    CREATE TABLE rating (
        player VARCHAR(64) NOT NULL,
        game VARCHAR(64) NOT NULL,
        rating INTEGER NOT NULL,
        ratedon TIMESTAMP NOT NULL
    );
     */

/**
 * This class provide service for rating via JDBC
 */

public class RatingServiceJDBC implements RatingService {

    private static final String URL = "jdbc:mysql://localhost:3306/gamestudio";
    private static final String USER = "root";
    private static final String PASSWORD = "benqfp7ig";

    private static final String INSERT_RATING =
            "INSERT INTO rating (player, game, rating, ratedon) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE rating = ?";

    private static final String SELECT_RATING =
            "SELECT player, game, rating, ratedon FROM rating WHERE game = 'spider-solitaire' AND player = ?";

    private static final String SELECT_AVERAGE_RATING =
            "SELECT player, game, rating, ratedon FROM rating WHERE game = 'spider-solitaire'";

    /**
     * This method saves rating to database
     *
     * @param rating rating which is gonna to be saved to database
     * @throws RatingException
     */

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RATING)) {
                preparedStatement.setString(1, rating.getPlayer());
                preparedStatement.setString(2, rating.getGame());
                preparedStatement.setInt(3, rating.getRating());
                preparedStatement.setDate(4, new Date(rating.getRatedon().getTime()));
                preparedStatement.setInt(5, rating.getRating());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Error saving rating", e);
        }
    }

    /**
     * This method returns average rating
     *
     * @param game type of game
     * @return average rating
     * @throws RatingException
     */

    @Override
    public int getAverageRating(String game) throws RatingException {
        List<Rating> ratings = new ArrayList<>();
        int averageRating = 0;
        int summmaryRating = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVERAGE_RATING)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Rating rating;
                        rating = new Rating(
                                resultSet.getString(1),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(4)
                        );
                        ratings.add(rating);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading rating", e);
        }
        if (ratings.size() > 0) {
            for (Rating rating : ratings) {
                summmaryRating += rating.getRating();
            }
            averageRating = summmaryRating / ratings.size();
        }
        return averageRating;
    }

    /**
     * This method get rating by player
     *
     * @param game   type of game
     * @param player name of player which played game
     * @return best rating of concrete player
     * @throws RatingException
     * @throws NullPointerException
     */

    @Override
    public int getRating(String game, String player) throws RatingException, NullPointerException {
        Rating rating = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RATING)) {
                preparedStatement.setString(1, player);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        rating = new Rating(
                                resultSet.getString(1),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(4)
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading rating", e);
        }
        return rating.getRating();
    }
}
