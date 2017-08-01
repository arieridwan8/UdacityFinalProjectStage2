package id.arieridwan.jagatcinema.features.detail;

import android.content.ContentResolver;

import id.arieridwan.jagatcinema.base.BasePresenter;
import id.arieridwan.jagatcinema.data.models.DataBottom;
import id.arieridwan.jagatcinema.data.models.Favourite;
import id.arieridwan.jagatcinema.data.models.FavouriteDao;
import id.arieridwan.jagatcinema.data.models.Review;
import id.arieridwan.jagatcinema.data.models.DataTop;
import id.arieridwan.jagatcinema.data.models.Trailer;
import id.arieridwan.jagatcinema.data.DBHelper;
import id.arieridwan.jagatcinema.utils.PrefHelper;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by arieridwan on 17/06/2017.
 */

public class DetailPresenter extends BasePresenter<DetailView> {

    public DetailPresenter(DetailView view) {
        super.attachView(view);
    }

    public void loadData(int id, String apiKey) {
        view.startLoading();
        Observable observableTrailer = apiServices.getTrailer(id, apiKey);
        Observable observableReview = apiServices.getReview(id, apiKey);
        addSubscribe(Observable.zip(observableTrailer, observableReview,
                new Func2<Trailer, Review, DataBottom>() {
                    @Override
                    public DataBottom call(Trailer trailerDao, Review reviewDao) {
                        return new DataBottom(trailerDao, reviewDao);
                    }
                }),
                new Subscriber<DataBottom>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.stopAndError();
                    }

                    @Override
                    public void onNext(DataBottom dao) {
                        view.getDataSuccess(dao);
                        view.stopAndHide();
                    }
                });
    }

    public void addToFavourite(ContentResolver resolver, DataTop data) {
        FavouriteDao.saveFavourite(resolver, data);
        view.callBackFavourite(true);
    }

    public void removeFromFavourite(ContentResolver resolver, int id) {
        FavouriteDao.removeFavourite(resolver, id);
        view.callBackFavourite(false);
    }

}
