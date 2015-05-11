package pl.rr.extradowcipy.ui.jokes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import pl.rr.extradowcipy.R;
import pl.rr.extradowcipy.model.Joke;

/**
 * Created by Rafal on 2015-04-21.
 */
public class JokesRecyclerAdapter extends RecyclerView.Adapter<JokesRecyclerViewHolder> {

    private static final int MAX_LINES = 2;
    private Context context;
    private List<Joke> jokes = Collections.emptyList();
    private int expandItem = -1;
    private int prevExpandItem = -1;
    private FavoriteJokeListener mListener;


    public JokesRecyclerAdapter(Context context, List<Joke> jokes, FavoriteJokeListener listener) {
        this.context = context;
        this.jokes = jokes;
        mListener = listener;
    }

    public JokesRecyclerAdapter(Context context, List<Joke> jokes,  FavoriteJokeListener listener, int currentItem, int prevItem) {
        this.context = context;
        this.jokes = jokes;
        mListener = listener;
        expandItem = currentItem;
        prevExpandItem = prevItem;
    }

    @Override
    public JokesRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.jokes_item, viewGroup, false);
        return new JokesRecyclerViewHolder(view, new JokesRecyclerViewHolder.JokesItemOnClickListener() {
            @Override
            public void onFavClick(int position) {
                updateItem(position);
                if(mListener != null)
                    mListener.updateFavoriteItem(jokes.get(position).getId(), jokes.get(position).isFav());
            }

            @Override
            public void onExpandCollapseClick(int position) {
                expandCollapse(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(JokesRecyclerViewHolder jokesRecyclerViewHolder, int i) {
        jokesRecyclerViewHolder.jokesCategory.setText(jokes.get(i).getCategory().getCategory());
        jokesRecyclerViewHolder.jokesContent.setText(jokes.get(i).getContent());

        if(jokes.get(i).isFav())
            jokesRecyclerViewHolder.favIv.setImageResource(R.drawable.ic_action_important);
        else
            jokesRecyclerViewHolder.favIv.setImageResource(R.drawable.ic_action_not_important);

        if(expandItem == i) {
            if(expandItem != prevExpandItem) {
                setViewHolderDependOnIsExpandOrNot(false, jokesRecyclerViewHolder);
            } else {
                prevExpandItem = -1;
                expandItem = -1;
                setViewHolderDependOnIsExpandOrNot(true, jokesRecyclerViewHolder);
            }
        } else {
            setViewHolderDependOnIsExpandOrNot(true, jokesRecyclerViewHolder);
        }


    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    private void setViewHolderDependOnIsExpandOrNot(boolean isExpand, JokesRecyclerViewHolder jokesRecyclerViewHolder) {
        if(isExpand) {
            jokesRecyclerViewHolder.jokesContent.setMaxLines(MAX_LINES);
            jokesRecyclerViewHolder.jokesContent.setEllipsize(TextUtils.TruncateAt.END);
            jokesRecyclerViewHolder.expandColapseIv.setImageResource(R.drawable.ic_action_expand);
        } else {
            jokesRecyclerViewHolder.jokesContent.setMaxLines(Integer.MAX_VALUE);
            jokesRecyclerViewHolder.jokesContent.setEllipsize(null);
            jokesRecyclerViewHolder.expandColapseIv.setImageResource(R.drawable.ic_action_collapse);
        }
    }

    public void updateItem(int pos) {
        jokes.get(pos).setFav(!jokes.get(pos).isFav());
        notifyItemChanged(pos);
    }

    public void expandCollapse(int pos) {
        if(expandItem >= 0)
            notifyItemChanged(expandItem);
        prevExpandItem = expandItem;
        expandItem = pos;
        notifyItemChanged(pos);
    }

    public int getExpandItem() {
        return expandItem;
    }

    public int getPrevExpandItem() {
        return prevExpandItem;
    }

    public interface FavoriteJokeListener {
        public void updateFavoriteItem(int id, boolean isFav);
    }

}
