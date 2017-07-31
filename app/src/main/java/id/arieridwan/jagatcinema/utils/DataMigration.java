package id.arieridwan.jagatcinema.utils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by arieridwan on 31/07/2017.
 */

public class DataMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            schema.create("Favourite")
                    .addField("title", String.class)
                    .addField("poster_path", String.class)
                    .addField("overview", String.class)
                    .addField("backdrop_path", String.class)
                    .addField("release_date", String.class)
                    .addField("favourite", Boolean.class)
                    .addField("id", int.class)
                    .addField("vote_average", double.class)
                    .addField("popularity", double.class);
            oldVersion++;
        }
    }

}
