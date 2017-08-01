package id.arieridwan.jagatcinema.data.models;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import id.arieridwan.jagatcinema.utils.Constants;
import id.arieridwan.jagatcinema.utils.PrefHelper;

import static android.content.Context.MODE_PRIVATE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.BACKDROP_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.CONTENT_URI;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.ID;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.OVERVIEW;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POPULARITY;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POSTER_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.RELEASE_DATE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.TITLE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.VOTE_AVERAGE;

/**
 * Created by arieridwan on 01/08/2017.
 */

public class FavouriteDao {

    public static void saveFavourite(ContentResolver resolver, DataTop dataTop) {
        if (dataTop.getMovie() != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, dataTop.getMovie().getId());
            contentValues.put(TITLE, dataTop.getMovie().getTitle());
            contentValues.put(POSTER_PATH, dataTop.getMovie().getPoster_path());
            contentValues.put(OVERVIEW, dataTop.getMovie().getOverview());
            contentValues.put(BACKDROP_PATH, dataTop.getMovie().getBackdrop_path());
            contentValues.put(RELEASE_DATE, dataTop.getMovie().getRelease_date());
            contentValues.put(VOTE_AVERAGE, dataTop.getMovie().getVote_average());
            contentValues.put(POPULARITY, dataTop.getMovie().getPopularity());
            resolver.insert(CONTENT_URI, contentValues);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, dataTop.getFavourite().getId());
            contentValues.put(TITLE, dataTop.getFavourite().getTitle());
            contentValues.put(POSTER_PATH, dataTop.getFavourite().getPoster_path());
            contentValues.put(OVERVIEW, dataTop.getFavourite().getOverview());
            contentValues.put(BACKDROP_PATH, dataTop.getFavourite().getBackdrop_path());
            contentValues.put(RELEASE_DATE, dataTop.getFavourite().getRelease_date());
            contentValues.put(VOTE_AVERAGE, dataTop.getFavourite().getVote_average());
            contentValues.put(POPULARITY, dataTop.getFavourite().getPopularity());
            resolver.insert(CONTENT_URI, contentValues);
        }
    }

    public static void removeFavourite(ContentResolver resolver, int id) {
        String stringId = String.valueOf(id);
        Uri uri = CONTENT_URI.buildUpon().appendPath(stringId).build();
        resolver.delete(uri, null, null);
    }

}
