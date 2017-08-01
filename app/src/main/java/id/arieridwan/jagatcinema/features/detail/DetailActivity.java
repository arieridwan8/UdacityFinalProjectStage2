package id.arieridwan.jagatcinema.features.detail;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.ReviewAdapter;
import id.arieridwan.jagatcinema.adapter.TrailerAdapter;
import id.arieridwan.jagatcinema.base.MvpActivity;
import id.arieridwan.jagatcinema.data.models.DataBottom;
import id.arieridwan.jagatcinema.data.models.Favourite;
import id.arieridwan.jagatcinema.data.models.Movie;
import id.arieridwan.jagatcinema.data.models.Review;
import id.arieridwan.jagatcinema.data.models.DataTop;
import id.arieridwan.jagatcinema.data.models.Trailer;
import id.arieridwan.jagatcinema.utils.Constants;
import id.arieridwan.jagatcinema.utils.PrefHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends MvpActivity<DetailPresenter>
        implements DetailView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ivBackdrop)
    ImageView ivBackdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivPoster)
    ImageView ivPoster;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.tvPopular)
    TextView tvPopular;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.rv_trailer)
    RecyclerView rvTrailer;
    @BindView(R.id.rv_review)
    RecyclerView rvReview;
    @BindView(R.id.label5)
    TextView label5;
    @BindView(R.id.label6)
    TextView label6;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.detail_content)
    CoordinatorLayout detailContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    // movie
    private Movie.ResultsBean movieModel;
    // trailer
    private TrailerAdapter trailerAdapter;
    private LinearLayoutManager trailerLinearLayoutManager;
    private List<Trailer.ResultsBean> trailerList = new ArrayList<>();
    // review
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager reviewLinearLayoutManager2;
    private List<Review.ResultsBean> reviewList = new ArrayList<>();
    // favourite
    private boolean isFavourite = false;
    private Favourite favouriteModel;
    private ContentResolver contentResolver;

    @Override
    protected DetailPresenter onCreatePresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkExtra();
    }

    private void checkExtra() {
        Intent i = getIntent();
        if (i.hasExtra(Constants.MOVIE_MODEL)) {
            movieModel = Parcels.unwrap(getIntent()
                    .getParcelableExtra(Constants.MOVIE_MODEL));
            initView();
            // need to check
            boolean favouriteState = PrefHelper.getStateChecking(this, String.valueOf(movieModel.getId()));
            getCheckedFavourite(favouriteState);
            presenter.loadData(movieModel.getId(), Constants.API_KEY);
        } else if (i.hasExtra(Constants.FAVOURITE_MODEL)) {
            favouriteModel = Parcels.unwrap(getIntent()
                    .getParcelableExtra(Constants.FAVOURITE_MODEL));
            initView();
            // no need to check
            isFavourite = true;
            checkFavourite();
            presenter.loadData(favouriteModel.getId(), Constants.API_KEY);
        } else {
            noData();
        }
    }

    private void initView() {
        contentResolver = getContentResolver();
        if (movieModel != null) {
            setMovieData();
        } else {
            setFavouriteData();
        }
        // trailer
        trailerAdapter = new TrailerAdapter(trailerList);
        trailerLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTrailer.setAdapter(trailerAdapter);
        rvTrailer.setLayoutManager(trailerLinearLayoutManager);
        // review
        reviewAdapter = new ReviewAdapter(reviewList);
        reviewLinearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReview.setAdapter(reviewAdapter);
        rvReview.setLayoutManager(reviewLinearLayoutManager2);
        swipeRefresh.setOnRefreshListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favourite();
            }
        });
    }

    private void setMovieData() {
        String backdrop_url = Constants.IMG_URL + movieModel.getBackdrop_path();
        String poster_url = Constants.IMG_URL + movieModel.getPoster_path();
        try {
            Picasso.with(this)
                    .load(backdrop_url)
                    .error(ContextCompat.getDrawable(this, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.place_holder))
                    .into(ivBackdrop);
            Picasso.with(this)
                    .load(poster_url)
                    .error(ContextCompat.getDrawable(this, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.place_holder))
                    .into(ivPoster);
        } catch (Exception e) {
            Log.e("getDataSuccess: ", e.getMessage());
        }
        tvTitle.setText(movieModel.getTitle());
        tvDate.setText(movieModel.getRelease_date());
        tvRate.setText(String.valueOf(movieModel.getVote_average()));
        tvPopular.setText(String.valueOf(movieModel.getPopularity()));
        tvOverview.setText(movieModel.getOverview());
    }

    private void setFavouriteData() {
        String backdrop_url = Constants.IMG_URL + favouriteModel.getBackdrop_path();
        String poster_url = Constants.IMG_URL + favouriteModel.getPoster_path();
        try {
            Picasso.with(this)
                    .load(backdrop_url)
                    .error(ContextCompat.getDrawable(this, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.place_holder))
                    .into(ivBackdrop);
            Picasso.with(this)
                    .load(poster_url)
                    .error(ContextCompat.getDrawable(this, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(this, R.drawable.place_holder))
                    .into(ivPoster);
        } catch (Exception e) {
            Log.e("getDataSuccess: ", e.getMessage());
        }
        tvTitle.setText(favouriteModel.getTitle());
        tvDate.setText(favouriteModel.getRelease_date());
        tvRate.setText(String.valueOf(favouriteModel.getVote_average()));
        tvPopular.setText(String.valueOf(favouriteModel.getPopularity()));
        tvOverview.setText(favouriteModel.getOverview());
    }

    private void checkFavourite() {
        if (isFavourite) {
            fab.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    private void favourite() {
        if (movieModel != null) {
            if (isFavourite) {
                isFavourite = false;
                presenter.removeFromFavourite(contentResolver, movieModel.getId());
                fab.setImageResource(R.drawable.ic_favorite_border_24dp);
            } else {
                isFavourite = true;
                presenter.addToFavourite(contentResolver, new DataTop(movieModel, favouriteModel));
                fab.setImageResource(R.drawable.ic_favorite_24dp);
            }
        } else {
            if (isFavourite) {
                isFavourite = false;
                presenter.removeFromFavourite(contentResolver, favouriteModel.getId());
                fab.setImageResource(R.drawable.ic_favorite_border_24dp);
            } else {
                isFavourite = true;
                presenter.addToFavourite(contentResolver, new DataTop(movieModel, favouriteModel));
                fab.setImageResource(R.drawable.ic_favorite_24dp);
            }
        }
    }

    private void noData() {
        Snackbar.make(detailContent, getString(R.string.error_text), Snackbar.LENGTH_SHORT).show();
    }

    private void addedToFavourite() {
        Snackbar.make(detailContent, getString(R.string.added), Snackbar.LENGTH_SHORT).show();
        setStateFavouriteTrue();
    }

    private void setStateFavouriteTrue() {
        if(movieModel != null) {
            PrefHelper.setStateChecking(this, String.valueOf(movieModel.getId()), true);
        } else {
            PrefHelper.setStateChecking(this, String.valueOf(favouriteModel.getId()), true);
        }
    }

    private void removedFromFavourite() {
        Snackbar.make(detailContent, getString(R.string.removed), Snackbar.LENGTH_SHORT).show();
        setStateFavouriteFalse();
    }

    private void setStateFavouriteFalse() {
        if(movieModel != null) {
            PrefHelper.setStateChecking(this, String.valueOf(movieModel.getId()), false);
        } else {
            PrefHelper.setStateChecking(this, String.valueOf(favouriteModel.getId()), false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            supportFinishAfterTransition();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void stopAndHide() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void stopAndError() {
        swipeRefresh.setRefreshing(false);
        noData();
    }

    @Override
    public void getDataSuccess(DataBottom item) {
        if (item.getTrailerDao() != null) {
            trailerList.addAll(item.getTrailerDao().getResults());
            reviewList.addAll(item.getReviewDao().getResults());
        } else {
            label5.setVisibility(View.GONE);
        }
        if (item.getReviewDao() != null) {
            trailerAdapter.notifyDataSetChanged();
            reviewAdapter.notifyDataSetChanged();
        } else {
            label6.setVisibility(View.GONE);
        }
    }

    @Override
    public void callBackFavourite(boolean isFavourite) {
        if (isFavourite) {
            addedToFavourite();
        } else {
            removedFromFavourite();
        }
    }

    private void getCheckedFavourite(boolean favourite) {
        isFavourite = favourite;
        Log.e("getCheckedFavourite: ", favourite+"");
        checkFavourite();
    }

    @Override
    public void onRefresh() {
        if (movieModel != null) {
            presenter.loadData(movieModel.getId(), Constants.API_KEY);
        } else {
            presenter.loadData(favouriteModel.getId(), Constants.API_KEY);
        }
    }

}
