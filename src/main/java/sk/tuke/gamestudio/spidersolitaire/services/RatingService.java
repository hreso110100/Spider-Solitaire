package sk.tuke.gamestudio.spidersolitaire.services;

import sk.tuke.gamestudio.spidersolitaire.entities.Rating;

/**
 * Interface for rating to set, get methods and to get average rating
 */

public interface RatingService {
    void setRating(Rating rating) throws RatingException;

    int getAverageRating(String game) throws RatingException;

    int getRating(String game, String player) throws RatingException;
}
