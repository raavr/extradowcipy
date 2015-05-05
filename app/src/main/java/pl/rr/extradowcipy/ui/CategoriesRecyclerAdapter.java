package pl.rr.extradowcipy.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import pl.rr.extradowcipy.R;
import pl.rr.extradowcipy.model.Category;

/**
 * Created by Rafal on 2015-04-21.
 */
public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewHolder> {

    private List<Category> categories = Collections.emptyList();
    private Context context;

    public CategoriesRecyclerAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }
    @Override
    public CategoriesRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.categories_item, viewGroup, false);
        return new CategoriesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesRecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.category_title.setText(categories.get(i).getCategory());
        recyclerViewHolder.imageView.setHeightRatio(1d);
        recyclerViewHolder.imageView.setBackgroundResource(categories.get(i).getImageId());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
