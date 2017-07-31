package id.arieridwan.jagatcinema.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import id.arieridwan.jagatcinema.R;
import id.arieridwan.jagatcinema.adapter.holder.ReviewViewHolder;
import id.arieridwan.jagatcinema.models.Review;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<Review.ResultsBean> mList = new ArrayList<>();
    private Review.ResultsBean mData;

    public ReviewAdapter(List<Review.ResultsBean> mList) {
        this.mList = mList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, int position) {
        final int pos = position;
        mData = mList.get(pos);
        holder.tvAuthor.setText(mData.getAuthor());
        holder.tvContent.setText(mData.getContent());
        holder.tvContent.setMaxLines(3);
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvContent.setMaxLines(3);
                holder.tvMore.setVisibility(view.VISIBLE);
            }
        });
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvContent.setMaxLines(Integer.MAX_VALUE);
                holder.tvMore.setVisibility(view.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
