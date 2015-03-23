package pl.rr.extradowcipy.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.rr.extradowcipy.model.db.dbCategory;
import pl.rr.extradowcipy.model.db.dbFavorite;
import pl.rr.extradowcipy.model.db.dbJoke;

/**
 * Created by Rafal on 2014-11-13.
 */
public class DatabaseManager {

    private static DatabaseManager databaseManager;
    private DatabaseHelper databaseHelper;

    public static void init(Context c) {
        if(databaseManager == null)
            databaseManager = new DatabaseManager(c);
    }

    public static DatabaseManager getInstance() {
        return databaseManager;
    }


    private DatabaseManager(Context context) {
        if(databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    private DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public void releaseHelper() {
        if(databaseHelper != null && databaseManager != null) {
            databaseManager = null;
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public void addCategory(dbCategory category) { getHelper().getdbCategoryDao().create(category);}

    public void addJoke(dbJoke joke) { getHelper().getdbJokeDao().create(joke);}

    public void addFavorite(dbFavorite fav) { getHelper().getdbFavoriteDao().create(fav); }

    public void deleteCategoryByName(String name) {
        try {
            DeleteBuilder<dbCategory, Integer> deleteBuilder = getHelper().getdbCategoryDao().deleteBuilder();
            deleteBuilder.where().eq("name", name);
            deleteBuilder.delete();
        }catch (SQLException e) {

        }
    }

    public void deleteFavorite(dbJoke joke) {
        try {
            DeleteBuilder<dbFavorite, Integer> deleteBuilder = getHelper().getdbFavoriteDao().deleteBuilder();
            deleteBuilder.where().eq("joke_id", joke);
            deleteBuilder.delete();
        }catch (SQLException e) {

        }
    }

    public List<dbJoke> getJokesByCategory(dbCategory category) {
        return getHelper().getdbJokeDao().queryForEq("category_id", category);
    }

    public List<dbJoke> getAllJokes() {
        return getHelper().getdbJokeDao().queryForAll();
    }

    public List<dbCategory> getAllCategory() {
        return getHelper().getdbCategoryDao().queryForAll();
    }

    public List<dbCategory> getCategoryByName(String name) {
        return getHelper().getdbCategoryDao().queryForEq("name", name);
    }

    public List<dbFavorite> getAllFavoriteJokes() {
        return getHelper().getdbFavoriteDao().queryForAll();
    }

    public dbJoke getJokeById(int id) {
        return getHelper().getdbJokeDao().queryForId(id);
    }
}
