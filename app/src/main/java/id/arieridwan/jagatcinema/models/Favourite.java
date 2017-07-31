package id.arieridwan.jagatcinema.models;

import android.os.Parcelable;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by arieridwan on 30/07/2017.
 */

@Setter
@Getter
public class Favourite extends RealmObject implements Parcelable {
    private String title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private boolean favourite;
    @PrimaryKey
    private int id;
    private double vote_average;
    private double popularity;
    public Favourite() {
    }
    public Favourite(String title, String poster_path,
                     String overview, String backdrop_path,
                     String release_date, boolean favourite,
                     int id, double vote_average, double popularity) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.favourite = favourite;
        this.id = id;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }
    protected Favourite(android.os.Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        favourite = in.readByte() != 0;
        id = in.readInt();
        vote_average = in.readDouble();
        popularity = in.readDouble();
    }
    public static final Creator<Favourite> CREATOR = new Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(android.os.Parcel in) {
            return new Favourite(in);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(backdrop_path);
        parcel.writeString(release_date);
        parcel.writeByte((byte) (favourite ? 1 : 0));
        parcel.writeInt(id);
        parcel.writeDouble(vote_average);
        parcel.writeDouble(popularity);
    }
}
