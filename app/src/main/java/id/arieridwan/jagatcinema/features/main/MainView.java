package id.arieridwan.jagatcinema.features.main;

import id.arieridwan.jagatcinema.models.Favourite;
import id.arieridwan.jagatcinema.models.Movie;
import io.realm.RealmResults;

/**
 * Created by arieridwan on 17/06/2017.
 */

public interface MainView {
    void startLoading();
    void stopAndHide();
    void stopAndError();
    void getDataSuccess(Movie item, String filter);
    void getFavourite(RealmResults<Favourite> results);
}
