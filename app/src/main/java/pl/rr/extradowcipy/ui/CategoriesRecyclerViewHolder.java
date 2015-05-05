package pl.rr.extradowcipy.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.etsy.android.grid.util.DynamicHeightImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rr.extradowcipy.R;

/**
 * Created by Rafal on 2015-04-21.
 */
public class CategoriesRecyclerViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.category_title)
    RobotoTextView category_title;
    @InjectView(R.id.imgView)
    DynamicHeightImageView imageView;
    @InjectView(R.id.expand_arrow)
    ImageView expandImgView;

    public CategoriesRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
