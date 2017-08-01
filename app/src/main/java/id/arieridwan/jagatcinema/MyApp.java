package id.arieridwan.jagatcinema;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/calibri_0.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
