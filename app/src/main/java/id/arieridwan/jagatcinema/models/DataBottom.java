package id.arieridwan.jagatcinema.models;

import lombok.Getter;

/**
 * Created by arieridwan on 30/07/2017.
 */

@Getter
public class DataBottom {
    private Trailer trailerDao;
    private Review reviewDao;
    public DataBottom(Trailer trailerDao, Review reviewDao) {
        this.trailerDao = trailerDao;
        this.reviewDao = reviewDao;
    }
}
