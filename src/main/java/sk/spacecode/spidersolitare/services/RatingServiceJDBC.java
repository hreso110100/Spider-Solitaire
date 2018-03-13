package sk.spacecode.spidersolitare.services;

import sk.spacecode.spidersolitare.entities.Rating;
import sk.spacecode.spidersolitare.entities.Score;

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

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RATING)) {
                preparedStatement.setString(1, rating.getPlayer());
                preparedStatement.setString(2, rating.getGame());
                preparedStatement.setInt(3, rating.getRating());
                preparedStatement.setDate(4, new Date(rating.getRatedon().getTime()));
                preparedStatement.setInt(5,rating.getRating());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Error saving rating", e);
        }
    }

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
