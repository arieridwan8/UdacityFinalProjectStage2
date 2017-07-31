package id.arieridwan.jagatcinema.models;

import java.util.List;
import lombok.Getter;

/**
 * Created by arieridwan on 18/06/2017.
 */

@Getter
public class Trailer {
    private int id;
    private List<ResultsBean> results;
    public Trailer() {
    }
    public Trailer(int id, List<ResultsBean> results) {
        this.id = id;
        this.results = results;
    }
    @Getter
    public static class ResultsBean {
        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;
        public ResultsBean() {
        }
        public ResultsBean(String id, String iso_639_1,
                           String iso_3166_1, String key,
                           String name, String site,
                           int size, String type) {
            this.id = id;
            this.iso_639_1 = iso_639_1;
            this.iso_3166_1 = iso_3166_1;
            this.key = key;
            this.name = name;
            this.site = site;
            this.size = size;
            this.type = type;
        }
    }
}
