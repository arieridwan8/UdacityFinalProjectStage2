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

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_thumbnail)
    public ImageView mImageThumbnail;
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}