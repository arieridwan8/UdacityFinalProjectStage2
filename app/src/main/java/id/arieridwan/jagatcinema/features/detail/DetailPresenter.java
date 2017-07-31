package id.arieridwan.jagatcinema.features.detail;

import id.arieridwan.jagatcinema.base.BasePresenter;
import id.arieridwan.jagatcinema.models.DataBottom;
import id.arieridwan.jagatcinema.models.FavouriteDao;
import id.arieridwan.jagatcinema.models.Review;
import id.arieridwan.jagatcinema.models.DataTop;
import id.arieridwan.jagatcinema.models.Trailer;
import io.realm.Realm;
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

    public void addToFavourite(Realm realm, DataTop data) {
        if(data.getMovie() != null) {
            FavouriteDao.addToFavourite(realm, data);
            view.callBackFavourite(true);
        } else {
            FavouriteDao.addToFavourite(realm, data);
            view.callBackFavourite(true);
        }
    }

    public void removeFromFavourite(Realm realm, int id) {
        FavouriteDao.removeFromFavourite(realm, id);
        view.callBackFavourite(false);
    }

    public void checkFavourite(Realm realm, int id) {
        boolean isFavourite = FavouriteDao.checkFavourite(realm, id);
        view.getCheckedFavourite(isFavourite);
    }

}
