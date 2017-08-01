package id.arieridwan.jagatcinema.features.main;

import android.util.Log;
import id.arieridwan.jagatcinema.base.BasePresenter;
import id.arieridwan.jagatcinema.data.models.Movie;
import id.arieridwan.jagatcinema.data.DBHelper;
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

}
