package id.arieridwan.jagatcinema.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import id.arieridwan.jagatcinema.data.contract.MovieContract;
import id.arieridwan.jagatcinema.data.DBHelper;

/**
 * Created by arieridwan on 31/07/2017.
 */

public class MovieProvider  extends ContentProvider {

    private DBHelper dBhelper;

    public static final int FAVOURITE_MOVIES = 100;
    public static final int FAVOURITE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVOURITE_MOVIES, FAVOURITE_MOVIES);
        sUriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVOURITE_MOVIES +"/#", FAVOURITE_MOVIE_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        dBhelper = new DBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db = dBhelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match){
            case FAVOURITE_MOVIES:
                returnCursor = db.query(MovieContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVOURITE_MOVIE_WITH_ID:
                String stringId = uri.getPathSegments().get(1);
                Log.e("query: ", stringId);
                returnCursor = db.query(MovieContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        "id =?", new String[]{stringId},
                        null,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case FAVOURITE_MOVIES:
                long id = db.insert(MovieContract.FavouriteEntry.TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.FavouriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int returnDeletedMovies;

        switch (match) {
            case FAVOURITE_MOVIE_WITH_ID:
                String stringId = uri.getPathSegments().get(1);
                returnDeletedMovies = db.delete(MovieContract.FavouriteEntry.TABLE_NAME, "id=?", new String[]{stringId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (returnDeletedMovies !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnDeletedMovies;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
