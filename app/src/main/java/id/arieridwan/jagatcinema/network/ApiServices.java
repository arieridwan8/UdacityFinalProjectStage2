package id.arieridwan.jagatcinema.network;

import id.arieridwan.jagatcinema.data.models.Movie;
import id.arieridwan.jagatcinema.data.models.Review;
import id.arieridwan.jagatcinema.data.models.Trailer;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by arieridwan on 16/06/2017.
 */

public interface ApiServices {
    @GET("movie/{filter}")
    Observable<Movie> getPopularMovie(@Path("filter") String filter, @Query("api_key") String apiKey);
    @GET("movie/{id}/videos")
    Observable<Trailer> getTrailer(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("movie/{id}/reviews")
    Observable<Review> getReview(@Path("id") int id, @Query("api_key") String apiKey);
}
