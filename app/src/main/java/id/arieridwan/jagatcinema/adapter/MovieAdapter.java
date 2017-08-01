package id.arieridwan.jagatcinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;
import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.holder.MovieViewHolder;
import id.arieridwan.jagatcinema.features.detail.DetailActivity;
import id.arieridwan.jagatcinema.data.models.Movie;
import id.arieridwan.jagatcinema.utils.Constants;

/**
 * Created by arieridwan on 16/06/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context mContext;
    private List<Movie.ResultsBean> mList;
    private Movie.ResultsBean mData;

    public MovieAdapter(List<Movie.ResultsBean> mList) {
        this.mList = mList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        mContext = parent.getContext();
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final int pos = position;
        mData = mList.get(pos);
        holder.tvTitle.setText(mData.getTitle());
        String image_url = Constants.IMG_URL + mData.getPoster_path();
        try {
            Picasso.with(mContext)
                    .load(image_url)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.place_holder))
                    .into(holder.mImageThumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDetail(view, pos);
            }
        });
    }

    private void moveToDetail(View view, int pos) {
        Intent i = new Intent(view.getContext(), DetailActivity.class);
        i.putExtra(Constants.MOVIE_MODEL, Parcels.wrap(mList.get(pos)));
        view.getContext().startActivity(i);
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

}
