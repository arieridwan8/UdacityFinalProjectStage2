package id.arieridwan.jagatcinema.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

/**
 * Created by arieridwan on 16/06/2017.
 */

@Parcel
@Getter
public class MovieDao implements Serializable {
    private int page;
    private int total_results;
    private int total_pages;
    private List<ResultsBean> results;
    public MovieDao() {
    }
    public MovieDao(int page, int total_results,
                    int total_pages, List<ResultsBean> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }
    @Parcel
    @Getter
    public static class ResultsBean implements Serializable {
        private int vote_count;
        private int id;
        private boolean video;
        private double vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private List<Integer> genre_ids;
        public ResultsBean() {
        }
        public ResultsBean(int vote_count, int id, boolean video,
                           double vote_average, String title, double popularity,
                           String poster_path, String original_language,
                           String original_title, String backdrop_path,
                           boolean adult, String overview, String release_date,
                           List<Integer> genre_ids) {
            this.vote_count = vote_count;
            this.id = id;
            this.video = video;
            this.vote_average = vote_average;
            this.title = title;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.original_language = original_language;
            this.original_title = original_title;
            this.backdrop_path = backdrop_path;
            this.adult = adult;
            this.overview = overview;
            this.release_date = release_date;
            this.genre_ids = genre_ids;
        }
    }
}
