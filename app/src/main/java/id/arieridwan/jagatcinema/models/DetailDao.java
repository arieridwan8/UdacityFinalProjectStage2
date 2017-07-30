package id.arieridwan.jagatcinema.models;

import lombok.Getter;

/**
 * Created by arieridwan on 30/07/2017.
 */

@Getter
public class DetailDao {
    private TrailerDao trailerDao;
    private ReviewDao reviewDao;
    public DetailDao(TrailerDao trailerDao, ReviewDao reviewDao) {
        this.trailerDao = trailerDao;
        this.reviewDao = reviewDao;
    }
}
