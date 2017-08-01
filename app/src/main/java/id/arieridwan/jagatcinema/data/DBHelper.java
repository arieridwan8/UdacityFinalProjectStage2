package id.arieridwan.jagatcinema.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.BACKDROP_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.ID;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.OVERVIEW;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POPULARITY;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POSTER_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.RELEASE_DATE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.TABLE_NAME;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.TITLE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.VOTE_AVERAGE;

/**
 * Created by arieridwan on 31/07/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "FAVOURITE_DB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "+
                TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY," +
                TITLE + " TEXT, "+
                POSTER_PATH + " TEXT, "+
                OVERVIEW + " TEXT, "+
                BACKDROP_PATH + " TEXT, "+
                RELEASE_DATE + " TEXT, "+
                VOTE_AVERAGE + " DOUBLE, " +
                POPULARITY + " DOUBLE " +
                ");";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        this.onCreate(db);
    }

}
