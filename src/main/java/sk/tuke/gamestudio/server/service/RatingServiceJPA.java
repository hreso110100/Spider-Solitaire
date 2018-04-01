package sk.tuke.gamestudio.server.service;

import com.sun.webkit.dom.RangeImpl;
import sk.tuke.gamestudio.server.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException {
        
    }

    @Override
    public int getAverageRating(String game) throws RatingException {

        int averageRating = 0;
        int summmaryRating = 0;

        List<Rating> ratings = new ArrayList<Rating>(entityManager.createNamedQuery("Rating.getAverageRating")
                .setParameter("game", game).getResultList());

        if (ratings.size() > 0) {
            for (Rating rating : ratings) {
                summmaryRating += rating.getRating();
            }
            averageRating = summmaryRating / ratings.size();
        }
        return averageRating;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Rating rating = (Rating) entityManager.createNamedQuery("Rating.getRatingByPlayer")
                .setParameter("game", game).setParameter("player", player).getSingleResult();

        return rating.getRating();
    }
}
