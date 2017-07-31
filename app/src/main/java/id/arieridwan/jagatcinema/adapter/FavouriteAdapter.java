package id.arieridwan.jagatcinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.holder.FavouriteViewHolder;
import id.arieridwan.jagatcinema.features.detail.DetailActivity;
import id.arieridwan.jagatcinema.models.Favourite;
import id.arieridwan.jagatcinema.utils.Constants;

/**
 * Created by arieridwan on 31/07/2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

    private List<Favourite> mList = new ArrayList<>();
    private Favourite mData;
    private Context mContext;

    public FavouriteAdapter(List<Favourite> mList) {
        this.mList = mList;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        mContext = parent.getContext();
        return new FavouriteViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        final int pos = position;
        mData = mList.get(pos);
        holder.tvTitle.setText(mData.getTitle());
        String image_url = Constants.IMG_URL + mData.getPoster_path();
        try {
            Picasso.with(mContext)
                    .load(image_url)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.no_image))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.place_holder))
                    .into(holder.ivThumbnail);
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
        i.putExtra(Constants.FAVOURITE_MODEL, (Parcelable) mList.get(pos));
        view.getContext().startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
