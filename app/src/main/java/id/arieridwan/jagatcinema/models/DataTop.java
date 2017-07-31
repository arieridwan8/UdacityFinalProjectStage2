package id.arieridwan.jagatcinema.models;

import lombok.Getter;

/**
 * Created by arieridwan on 31/07/2017.
 */

@Getter
public class DataTop {
    private Movie.ResultsBean movie;
    private Favourite favourite;
    public DataTop() {
    }
    public DataTop(Movie.ResultsBean movie, Favourite favourite) {
        this.movie = movie;
        this.favourite = favourite;
    }
}
