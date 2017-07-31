package id.arieridwan.jagatcinema.features.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import id.arieridwan.jagatcinema.models.Favourite;
import id.arieridwan.jagatcinema.models.Movie;
import id.arieridwan.jagatcinema.utils.Constants;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends MvpActivity<MainPresenter>
        implements MainView, SwipeRefreshLayout.OnRefreshListener {

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

    private GridLayoutManager mLayoutManager;
    private AlertDialog alertDialog1;
    private int CURRENT_TYPE = 0;

    private Realm realm = Realm.getDefaultInstance();

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

    @Override
    public void getFavourite(RealmResults<Favourite> results) {
        String text = "Favourite";
        tvFilter.setText(text);
        mList2.clear();
        mAdapter2.notifyDataSetChanged();
        mList2.addAll(results);
        mAdapter2.notifyDataSetChanged();
        rvMovie.setAdapter(mAdapter2);
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
                presenter.loadFavourite(realm);
                break;
        }
        CURRENT_TYPE = type;
    }
}
