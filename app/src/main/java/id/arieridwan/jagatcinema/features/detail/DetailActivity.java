package id.arieridwan.jagatcinema.features.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import id.arieridwan.jagatcinema.models.DetailDao;
import id.arieridwan.jagatcinema.models.MovieDao;
import id.arieridwan.jagatcinema.models.ReviewDao;
import id.arieridwan.jagatcinema.models.TrailerDao;
import id.arieridwan.jagatcinema.utils.Constants;

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

    // movie
    private MovieDao.ResultsBean mData;
    // trailer
    private TrailerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<TrailerDao.ResultsBean> mList = new ArrayList<>();
    // review
    private ReviewAdapter mAdapter2;
    private LinearLayoutManager mLinearLayoutManager2;
    private List<ReviewDao.ResultsBean> mList2 = new ArrayList<>();

    @Override
    protected DetailPresenter onCreatePresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        if (i.hasExtra(Constants.MOVIE_DAO)) {
            mData = Parcels.unwrap(getIntent()
                    .getParcelableExtra(Constants.MOVIE_DAO));
            setData();
            presenter.loadTrailer(mData.getId(), Constants.API_KEY);
        } else {
            noData();
        }
    }

    private void setData() {
        String backdrop_url = Constants.IMG_URL + mData.getBackdrop_path();
        String poster_url = Constants.IMG_URL + mData.getPoster_path();
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
        tvTitle.setText(mData.getTitle());
        tvDate.setText(mData.getRelease_date());
        tvRate.setText(String.valueOf(mData.getVote_average()));
        tvPopular.setText(String.valueOf(mData.getPopularity()));
        tvOverview.setText(mData.getOverview());
        // trailer
        mAdapter = new TrailerAdapter(mList);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTrailer.setAdapter(mAdapter);
        rvTrailer.setLayoutManager(mLinearLayoutManager);
        // review
        mAdapter2 = new ReviewAdapter(mList2);
        mLinearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReview.setAdapter(mAdapter2);
        rvReview.setLayoutManager(mLinearLayoutManager2);
        swipeRefresh.setOnRefreshListener(this);
    }

    private void noData() {
        Snackbar.make(detailContent, getString(R.string.error_text), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
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
    public void getDataSuccess(DetailDao item) {
        if (item.getTrailerDao() != null) {
            mList.addAll(item.getTrailerDao().getResults());
            mList2.addAll(item.getReviewDao().getResults());
        } else {
            label5.setVisibility(View.GONE);
        }
        if (item.getReviewDao() != null) {
            mAdapter.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
        } else {
            label6.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        presenter.loadTrailer(mData.getId(), Constants.API_KEY);
    }

}
