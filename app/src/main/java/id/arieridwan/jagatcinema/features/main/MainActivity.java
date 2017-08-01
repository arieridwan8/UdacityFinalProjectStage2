package id.arieridwan.jagatcinema.features.main;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.FavouriteAdapter;
import id.arieridwan.jagatcinema.adapter.MovieAdapter;
import id.arieridwan.jagatcinema.base.MvpActivity;
import id.arieridwan.jagatcinema.data.contract.MovieContract;
import id.arieridwan.jagatcinema.data.models.Favourite;
import id.arieridwan.jagatcinema.data.models.Movie;
import id.arieridwan.jagatcinema.utils.Constants;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.BACKDROP_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.ID;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.OVERVIEW;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POPULARITY;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.POSTER_PATH;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.RELEASE_DATE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.TITLE;
import static id.arieridwan.jagatcinema.data.contract.MovieContract.FavouriteEntry.VOTE_AVERAGE;

public class MainActivity extends MvpActivity<MainPresenter>
        implements MainView, SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.floating_filter)
    LinearLayout floatingFilter;
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    // movie
    private List<Movie.ResultsBean> mList = new ArrayList<>();
    private MovieAdapter mAdapter;
    // favourite
    private List<Favourite> mList2 = new ArrayList<>();
    private FavouriteAdapter mAdapter2;
    private static final int MOVIE_LOADER_ID = 1;

    private GridLayoutManager mLayoutManager;
    private AlertDialog alertDialog1;
    private int CURRENT_TYPE = 0;

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new MovieAdapter(mList);
        mAdapter2 = new FavouriteAdapter(mList2);
        rvMovie.setAdapter(mAdapter);
        rvMovie.setLayoutManager(mLayoutManager);
        rvMovie.setItemAnimator(new DefaultItemAnimator());
        rvMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView v, int dx, int dy) {
                super.onScrolled(v, dx, dy);
                if (dy > 0) {
                    if (dy == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        floatingFilter.setVisibility(View.VISIBLE);
                    } else {
                        floatingFilter.setVisibility(View.GONE);
                    }
                } else {
                    floatingFilter.setVisibility(View.VISIBLE);
                }
            }
        });
        floatingFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });
        swipeRefresh.setOnRefreshListener(this);
        presenter.loadData(Constants.popular, Constants.API_KEY);
    }

    private void noData() {
        Snackbar.make(mainContent, getString(R.string.error_text), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        rvMovie.setVisibility(View.GONE);
        floatingFilter.setVisibility(View.GONE);
        tvFilter.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void stopAndHide() {
        swipeRefresh.setRefreshing(false);
        rvMovie.setVisibility(View.VISIBLE);
        floatingFilter.setVisibility(View.VISIBLE);
        tvFilter.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopAndError() {
        swipeRefresh.setRefreshing(false);
        noData();
    }

    @Override
    public void getDataSuccess(Movie item, String filter) {
        String text;
        if(filter.equals(Constants.popular)){
            text = "Popular";
        }else {
            text = "Top Rated";
        }
        tvFilter.setText(text);
        mList.clear();
        mList.addAll(item.getResults());
        mAdapter.notifyDataSetChanged();
        rvMovie.setAdapter(mAdapter);
    }


    private void filter() {
        String[] values = {getString(R.string.popular), getString(R.string.top_rated), getString(R.string.favorite)};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.filter_by));
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(item);
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        loadData(CURRENT_TYPE);
    }

    private void loadData(int type) {
        switch (type) {
            case 0:
                presenter.loadData(Constants.popular, Constants.API_KEY);
                break;
            case 1:
                presenter.loadData(Constants.top_rated, Constants.API_KEY);
                break;
            case 2:
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
                break;
        }
        CURRENT_TYPE = type;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMovieData = null;
            @Override
            protected void onStartLoading() {
                if (mMovieData !=null){
                    deliverResult(mMovieData);
                } else{
                    forceLoad();
                }
            }
            @Override
            public Cursor loadInBackground() {
                try{
                    Cursor cursor = getContentResolver().query(MovieContract.FavouriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.FavouriteEntry.ID);

                    return cursor;

                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorToListMovies(data);
        stopAndHide();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void cursorToListMovies(Cursor cursor){
        mList2.clear();
        mAdapter2.notifyDataSetChanged();
        while (cursor.moveToNext()){
            Favourite favourites = new Favourite();
            favourites.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            favourites.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            favourites.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
            favourites.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            favourites.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
            favourites.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
            favourites.setVote_average(cursor.getDouble(cursor.getColumnIndex(VOTE_AVERAGE)));
            favourites.setPopularity(cursor.getDouble(cursor.getColumnIndex(POPULARITY)));
            mList2.add(favourites);
            mAdapter2.notifyDataSetChanged();
        }
        String text = getString(R.string.favorite);
        tvFilter.setText(text);
        rvMovie.setAdapter(mAdapter2);
    }
}
