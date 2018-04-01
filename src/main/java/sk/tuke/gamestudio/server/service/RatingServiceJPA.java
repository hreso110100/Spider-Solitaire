package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (int) entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return (int) entityManager.createNamedQuery("Rating.getRatingByPlayer")
                .setParameter("game", game).setParameter("player", player).getSingleResult();
    }
}
