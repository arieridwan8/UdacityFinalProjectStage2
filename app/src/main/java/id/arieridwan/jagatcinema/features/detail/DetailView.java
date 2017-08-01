package id.arieridwan.jagatcinema.features.detail;

import id.arieridwan.jagatcinema.data.models.DataBottom;

/**
 * Created by arieridwan on 17/06/2017.
 */

public interface DetailView {
    void startLoading();
    void stopAndHide();
    void stopAndError();
    void getDataSuccess(DataBottom item);
    void callBackFavourite(boolean isFavourite);
}
