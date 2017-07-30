package id.arieridwan.jagatcinema.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.jagatcinema.R;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivTrailer)
    public ImageView ivTrailer;
    @BindView(R.id.ivPlay)
    public ImageView ivPlay;
    @BindView(R.id.tvTrailer)
    public TextView tvTrailer;
    public TrailerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
