package pl.rr.extradowcipy.ui.jokes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.rr.extradowcipy.R;

/**
 * Created by Rafal on 2015-04-21.
 */
public class JokesRecyclerViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.jokes_category)
    RobotoTextView jokesCategory;
    @InjectView(R.id.jokes_content)
    RobotoTextView jokesContent;
    @InjectView(R.id.expand_colapse_joke)
    ImageView expandColapseIv;
    @InjectView(R.id.add_to_fav_iv) ImageView favIv;

    @OnClick(R.id.add_to_fav_iv)
    public void onFavClick() {
        if(mListener != null)
            mListener.onFavClick(getAdapterPosition());
    }

    @OnClick(R.id.expand_colapse_joke)
    public void onExpandCollapseClick() {
        if(mListener != null)
            mListener.onExpandCollapseClick(getAdapterPosition());
    }


    private JokesItemOnClickListener mListener;

    public interface JokesItemOnClickListener {
        public void onFavClick(int position);
        public void onExpandCollapseClick(int position);
    }

    public JokesRecyclerViewHolder(View itemView, JokesItemOnClickListener listener) {
        super(itemView);
        mListener = listener;
        ButterKnife.inject(this, itemView);

    }
}
