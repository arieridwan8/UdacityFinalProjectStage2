package id.arieridwan.jagatcinema;

import android.app.Application;

import id.arieridwan.jagatcinema.utils.DataMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .migration(new DataMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
