package id.arieridwan.jagatcinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.holder.TrailerViewHolder;
import id.arieridwan.jagatcinema.models.Trailer;
import id.arieridwan.jagatcinema.utils.Constants;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<Trailer.ResultsBean> mList = new ArrayList<>();
    private Trailer.ResultsBean mData;
    private Context mContext;

    public TrailerAdapter(List<Trailer.ResultsBean> mList) {
        this.mList = mList;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_trailer, parent, false);
        mContext = parent.getContext();
        return new TrailerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final int pos = position;
        mData = mList.get(pos);
        holder.tvTrailer.setText(mData.getName());
        String image_url = Constants.THUMBNAILS_URL
                + mData.getKey()
                + Constants.THUMBNAILS_FORMAT;
        Picasso.with(mContext)
                .load(image_url)
                .error(ContextCompat.getDrawable(mContext, R.drawable.no_image))
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.place_holder))
                .into(holder.ivTrailer);
        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.YOUTUBE_WATCH + mData.getKey()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
