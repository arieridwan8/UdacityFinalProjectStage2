package id.arieridwan.jagatcinema.data.models;

import org.parceler.Parcel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by arieridwan on 30/07/2017.
 */

@Parcel
@Getter
@Setter
public class Favourite {
    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private double vote_average;
    private double popularity;
    public Favourite() {
    }
    public Favourite(int id, String title,
                     String poster_path, String overview,
                     String backdrop_path, String release_date,
                     double vote_average, double popularity) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }
}
