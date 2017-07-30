package id.arieridwan.jagatcinema.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.jagatcinema.R;

/**
 * Created by arieridwan on 30/07/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvAuthor)
    public TextView tvAuthor;
    @BindView(R.id.tvContent)
    public TextView tvContent;
    @BindView(R.id.tvMore)
    public TextView tvMore;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
