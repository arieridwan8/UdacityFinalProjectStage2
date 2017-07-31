package id.arieridwan.jagatcinema.features.main;

import android.util.Log;
import id.arieridwan.jagatcinema.base.BasePresenter;
import id.arieridwan.jagatcinema.models.Favourite;
import id.arieridwan.jagatcinema.models.FavouriteDao;
import id.arieridwan.jagatcinema.models.Movie;
import id.arieridwan.jagatcinema.utils.Constants;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;

/**
 * Created by arieridwan on 17/06/2017.
 */

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super.attachView(view);
    }

    public void loadData(final String filter, String apiKey) {
        view.startLoading();
        addSubscribe(apiServices.getPopularMovie(filter,apiKey),
                new Subscriber<Movie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("OnError", e.getMessage());
                view.stopAndError();
            }

            @Override
            public void onNext(Movie item) {
                view.getDataSuccess(item, filter);
                view.stopAndHide();
            }
        });
    }

    public void loadFavourite(Realm realm) {
        view.startLoading();
        RealmResults<Favourite> results = FavouriteDao.getFavourites(realm);
        view.getFavourite(results);
        view.stopAndHide();
    }

}
