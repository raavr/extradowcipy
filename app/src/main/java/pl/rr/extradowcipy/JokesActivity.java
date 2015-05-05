package pl.rr.extradowcipy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rr.extradowcipy.model.DatabaseManager;
import pl.rr.extradowcipy.model.Joke;
import pl.rr.extradowcipy.ui.JokesRecyclerAdapter;
import pl.rr.extradowcipy.ui.SpacesItemDecoration;


public class JokesActivity extends ActionBarActivity implements JokesRecyclerAdapter.FavoriteJokeListener{

    public static final String JOKES_INTENT = "jokes_intent";
    public static final String JOKES_CATEGORY_INTENT = "jokes_category_intent";

    private static final String CURRENT_ITEM_SELECTED = "current_item_selected";
    private static final String PREV_ITEM_SELECTED = "prev_item_selected";
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.recyclerViewJokes)
    RecyclerView recyclerViewJokes;

    private JokesRecyclerAdapter jokesRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);
        ButterKnife.inject(this, this);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        List<Joke> jokes = (ArrayList) intent.getParcelableArrayListExtra(JOKES_INTENT);
        mTitle = intent.getStringExtra(JOKES_CATEGORY_INTENT);
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(savedInstanceState != null) {
            int currentItemSelected = savedInstanceState.getInt(CURRENT_ITEM_SELECTED);
            int prevItemSelected = savedInstanceState.getInt(PREV_ITEM_SELECTED);
            jokesRecyclerAdapter = new JokesRecyclerAdapter(this, jokes, this, currentItemSelected, prevItemSelected);
        } else {
            jokesRecyclerAdapter = new JokesRecyclerAdapter(this, jokes, this);
        }
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewJokes.setHasFixedSize(true);
        recyclerViewJokes.setLayoutManager(layoutManager);
        recyclerViewJokes.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJokes.addItemDecoration(new SpacesItemDecoration(5));
        recyclerViewJokes.setAdapter(jokesRecyclerAdapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_ITEM_SELECTED, jokesRecyclerAdapter.getExpandItem());
        outState.putInt(PREV_ITEM_SELECTED, jokesRecyclerAdapter.getPrevExpandItem());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void updateFavoriteItem(int id, boolean isFav) {
        try {
            DatabaseManager.getInstance().updateJokeFavColumn(isFav, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
