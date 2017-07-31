package id.arieridwan.jagatcinema.models;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class FavouriteDao {

    public static void addToFavourite(Realm realm, final DataTop data) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(data.getMovie() != null) {
                    Favourite favourite = realm.createObject(Favourite.class, data.getMovie().getId());
                    favourite.setTitle(data.getMovie().getTitle());
                    favourite.setBackdrop_path(data.getMovie().getBackdrop_path());
                    favourite.setOverview(data.getMovie().getOverview());
                    favourite.setPopularity(data.getMovie().getPopularity());
                    favourite.setPoster_path(data.getMovie().getPoster_path());
                    favourite.setRelease_date(data.getMovie().getRelease_date());
                    favourite.setVote_average(data.getMovie().getVote_average());
                    favourite.setFavourite(true);
                } else {
                    Favourite favourite = realm.createObject(Favourite.class, data.getFavourite().getId());
                    favourite.setTitle(data.getFavourite().getTitle());
                    favourite.setBackdrop_path(data.getFavourite().getBackdrop_path());
                    favourite.setOverview(data.getFavourite().getOverview());
                    favourite.setPopularity(data.getFavourite().getPopularity());
                    favourite.setPoster_path(data.getFavourite().getPoster_path());
                    favourite.setRelease_date(data.getFavourite().getRelease_date());
                    favourite.setVote_average(data.getFavourite().getVote_average());
                    favourite.setFavourite(true);
                }
            }
        });
    }

    public static void removeFromFavourite(Realm realm, int id) {
        final Favourite favourite = realm.where(Favourite.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                favourite.deleteFromRealm();
            }
        });
    }

    public static RealmResults<Favourite> getFavourites(Realm realm) {
        RealmResults<Favourite> favourite = realm.where(Favourite.class).findAll();
        return favourite;
    }

    public static boolean checkFavourite(Realm realm, int id) {
        // default value
        boolean isFavourite = false;
        try {
            Favourite favourite = realm.where(Favourite.class).equalTo("id", id).findFirst();
            isFavourite = favourite.isFavourite();
        } catch (Exception e) {
            Log.e("checkFavourite: ", e.getMessage() + "");
        }
        return isFavourite;
    }

}
