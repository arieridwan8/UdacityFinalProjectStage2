package id.arieridwan.jagatcinema.features.main;

import java.util.List;
import id.arieridwan.jagatcinema.data.models.Favourite;
import id.arieridwan.jagatcinema.data.models.Movie;

/**
 * Created by arieridwan on 17/06/2017.
 */

public interface MainView {
    void startLoading();
    void stopAndHide();
    void stopAndError();
    void getDataSuccess(Movie item, String filter);
}
