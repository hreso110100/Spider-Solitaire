package sk.spacecode.spidersolitare.services;

import sk.spacecode.spidersolitare.entities.Rating;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
}
