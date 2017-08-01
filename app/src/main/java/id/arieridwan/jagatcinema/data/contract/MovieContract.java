package id.arieridwan.jagatcinema.data.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arieridwan on 31/07/2017.
 */

public class MovieContract {

    public static final String AUTHORITY = "id.arieridwan.jagatcinema";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITE_MOVIES = "favourite";

    public static final class FavouriteEntry {

        public static final Uri CONTENT_URI =
                MovieContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();

        public static final String TABLE_NAME = "favourite";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String POSTER_PATH = "poster_path";
        public static final String OVERVIEW = "overview";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String RELEASE_DATE = "release_Date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String POPULARITY = "popularity";

    }

}
