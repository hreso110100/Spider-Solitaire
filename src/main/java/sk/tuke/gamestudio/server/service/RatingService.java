package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Rating;

/**
 * Interface for rating to set, get methods and to get average rating
 */

public interface RatingService {
    void setRating(Rating rating) throws RatingException;

    int getAverageRating(String game) throws RatingException;

    int getRating(String game, String player) throws RatingException;
}
