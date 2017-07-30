package id.arieridwan.jagatcinema.models;

import java.util.List;
import lombok.Getter;

/**
 * Created by arieridwan on 18/06/2017.
 */

@Getter
public class ReviewDao {
    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ResultsBean> results;
    @Getter
    public static class ResultsBean {
        private String id;
        private String author;
        private String content;
        private String url;
    }
}
