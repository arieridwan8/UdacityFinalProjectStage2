package id.arieridwan.jagatcinema.features.detail;

import id.arieridwan.jagatcinema.base.BasePresenter;
import id.arieridwan.jagatcinema.models.DetailDao;
import id.arieridwan.jagatcinema.models.ReviewDao;
import id.arieridwan.jagatcinema.models.TrailerDao;
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

    public void loadTrailer(int id, String apiKey) {
        view.startLoading();
        Observable observableTrailer = apiServices.getTrailer(id, apiKey);
        Observable observableReview = apiServices.getReview(id, apiKey);
        addSubscribe(Observable.zip(observableTrailer, observableReview,
                new Func2<TrailerDao, ReviewDao, DetailDao>() {
                    @Override
                    public DetailDao call(TrailerDao trailerDao, ReviewDao reviewDao) {
                        return new DetailDao(trailerDao, reviewDao);
                    }
                }),
                new Subscriber<DetailDao>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.stopAndError();
            }

            @Override
            public void onNext(DetailDao dao) {
                view.getDataSuccess(dao);
                view.stopAndHide();
            }
        });
    }

}
