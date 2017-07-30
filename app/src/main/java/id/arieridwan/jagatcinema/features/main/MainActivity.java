package id.arieridwan.jagatcinema.features.main;

import android.content.DialogInterface;
import android.os.Bundle;
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
import id.arieridwan.jagatcinema.adapter.MovieAdapter;
import id.arieridwan.jagatcinema.base.MvpActivity;
import id.arieridwan.jagatcinema.models.MovieDao;
import id.arieridwan.jagatcinema.utils.Constants;

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

    private List<MovieDao.ResultsBean> mList = new ArrayList<>();
    private MovieAdapter mAdapter;
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
        rvMovie.setLayoutManager(new GridLayoutManager(this, 2));
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
        mAdapter = new MovieAdapter(mList);
        rvMovie.setAdapter(mAdapter);
        floatingFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });
        swipeRefresh.setOnRefreshListener(this);
        presenter.loadData(Constants.popular, Constants.API_KEY);
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
    }

    @Override
    public void getDataSuccess(MovieDao item) {
        mList.clear();
        mList.addAll(item.getResults());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setFilterText(String text) {
        tvFilter.setText(text);
    }

    private void filter() {
        String[] values = {getString(R.string.popular), getString(R.string.top_rated)};
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
    public void onRefresh() {
       loadData(CURRENT_TYPE);
    }

    private void loadData(int type){
        switch (type) {
            case 0:
                presenter.loadData(Constants.popular, Constants.API_KEY);
                break;
            case 1:
                presenter.loadData(Constants.top_rated, Constants.API_KEY);
                break;
            case 2:
                //TODO favorite
                break;
        }
        CURRENT_TYPE = type;
    }
}
