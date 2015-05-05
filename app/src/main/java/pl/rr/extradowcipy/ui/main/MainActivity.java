package pl.rr.extradowcipy.ui.main;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.rr.extradowcipy.ui.jokes.JokesActivity;
import pl.rr.extradowcipy.R;
import pl.rr.extradowcipy.model.Category;
import pl.rr.extradowcipy.model.db.DatabaseManager;
import pl.rr.extradowcipy.model.Joke;
import pl.rr.extradowcipy.model.db.DbCategory;
import pl.rr.extradowcipy.model.db.DbJoke;
import pl.rr.extradowcipy.ui.categories.CategoriesFragment;
import pl.rr.extradowcipy.ui.navdrawer.NDFragment;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity
        implements NDFragment.NavigationDrawerCallbacks,
                    CategoriesFragment.OnCategoriesFragmentInteractionListener {

    private static final String PREF_DATABASES_CREATED = "DATABASE_CREATED";
    private NDFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private CategoriesFragment mCategoriesFragment;
    private boolean mDatabasesCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabasesCreated = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREF_DATABASES_CREATED, false);
        if(!mDatabasesCreated) {
            DatabaseManager.getInstance().fillDatabase();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PREF_DATABASES_CREATED, true).apply();
        }

        mNavigationDrawerFragment = (NDFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        onSectionAttached(position);
        switch (position) {
              case 0:
                   fragmentManager.beginTransaction()
                            .replace(R.id.container, mCategoriesFragment = CategoriesFragment.newInstance())
                            .commit();
        }



    }

    @Override
    public void onNavigationDrawerItemSelected(String categoryName) {
        Observable<DbJoke> observable = null;
        try {
            observable = Observable.from(DatabaseManager.getInstance().getFavoriteJokesByCategoryName(categoryName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getJokesFromDB(observable);
    }

    public void onSectionAttached(int number) {
        String[] titles = getResources().getStringArray(R.array.nd_items_array);
        switch (number) {
            case 0:
                mTitle = titles[0];
                break;
            case 1:
                mTitle = titles[1];
                break;
            case 2:
                mTitle = titles[2];
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        mNavigationDrawerFragment.setMyFavoritesItem(getFavoritesJokesCategory());
        super.onResume();
    }

    @Override
    public void getCategoriesFromDB() {
        final List<Category> categories = new ArrayList<>();
        Observable<DbCategory> observable = Observable.from(DatabaseManager.getInstance().getAllCategory());
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DbCategory>() {
                    @Override
                    public void onCompleted() {
                        mCategoriesFragment.onSuccessCallback(categories);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DbCategory dbCat) {
                        categories.add(new Category(dbCat.getName(), getCategoryDrawableById(dbCat.getId())));
                    }
                });
    }

    @Override
    public void onCategoryClick(String categoryName) {
        Observable<DbJoke> observable = Observable.from(DatabaseManager.getInstance().getJokesByCategoryName(categoryName));
        getJokesFromDB(observable);

    }

    private void getJokesFromDB(Observable<DbJoke> observable) {
        final List<Joke> jokes = new ArrayList<>();
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DbJoke>() {
                    @Override
                    public void onCompleted() {
                        if(jokes.size() == 0)
                            Toast.makeText(getApplicationContext(), "Brak wynikow wyszukiwania", Toast.LENGTH_SHORT).show();
                        else
                            startJokesActivity(jokes);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DbJoke dbJoke) {
                        jokes.add(new Joke(dbJoke.getId(), dbJoke.getContent(), new Category(dbJoke.getCategory().getName(), R.drawable.ic_action_favorite), dbJoke.isFav()));
                    }
                });
    }



    private void startJokesActivity(List<Joke> jokes) {
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putParcelableArrayListExtra(JokesActivity.JOKES_INTENT, (ArrayList) jokes);
        intent.putExtra(JokesActivity.JOKES_CATEGORY_INTENT, jokes.get(0).getCategory().getCategory());
        startActivity(intent);

    }

    private HashMap<String, Integer> getFavoritesJokesCategory() {
        HashMap<String, Integer> categoryNameCountMap = new HashMap<>();
        List<DbCategory> categories = DatabaseManager.getInstance().getAllCategory();
        for(DbCategory category : categories) {
            int count = getFavoritesJokesCategoryCount(category);
            if(count > 0)
                categoryNameCountMap.put(category.getName(), count);
        }
        return  categoryNameCountMap;
    }

    private int getFavoritesJokesCategoryCount(DbCategory category) {
        int favCount = 0;
        try {
            List<DbJoke> favJokes = DatabaseManager.getInstance().getFavoriteJokesByCategory(category);
            favCount = favJokes.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favCount;
    }

    //tmp
    private int getCategoryDrawableById(int categoryId) {
        switch(categoryId) {
            case 1:
                return R.drawable.boy;
            case 2:
                return R.drawable.programmer;
            case 3:
                return R.drawable.animal;
            case 4:
                return R.drawable.school;
            default:
                return R.drawable.smile;
        }
    }

}
