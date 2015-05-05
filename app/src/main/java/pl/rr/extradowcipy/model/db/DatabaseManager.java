package pl.rr.extradowcipy.model.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.rr.extradowcipy.R;

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

    public void addCategory(DbCategory category) { getHelper().getdbCategoryDao().create(category);}

    public void addJoke(DbJoke joke) { getHelper().getdbJokeDao().create(joke);}

    public void addFavorite(DbFavorite fav) { getHelper().getdbFavoriteDao().create(fav); }

    public void deleteCategoryByName(String name) {
        try {
            DeleteBuilder<DbCategory, Integer> deleteBuilder = getHelper().getdbCategoryDao().deleteBuilder();
            deleteBuilder.where().eq("name", name);
            deleteBuilder.delete();
        }catch (SQLException e) {

        }
    }

    public void deleteFavorite(DbJoke joke) {
        try {
            DeleteBuilder<DbFavorite, Integer> deleteBuilder = getHelper().getdbFavoriteDao().deleteBuilder();
            deleteBuilder.where().eq("joke_id", joke);
            deleteBuilder.delete();
        }catch (SQLException e) {

        }
    }

    public List<DbJoke> getJokesByCategory(DbCategory category) {
        return getHelper().getdbJokeDao().queryForEq("category_id", category);
    }

    public List<DbJoke> getJokesByCategoryName(String categoryName) {
        return getHelper().getdbJokeDao().queryForEq("category_id", getCategoryByName(categoryName).get(0));
    }

    public List<DbJoke> getJokesByCategoryNameAndJoinFav(String categoryName) throws SQLException {
        QueryBuilder<DbCategory, Integer> queryBuilderCat = getHelper().getdbCategoryDao().queryBuilder();
        queryBuilderCat.where().eq("name", categoryName);
        QueryBuilder<DbJoke, Integer> queryBuilderJoke = getHelper().getdbJokeDao().queryBuilder();
        queryBuilderJoke.join(queryBuilderCat);
        QueryBuilder<DbFavorite, Integer> queryBuilderFav = getHelper().getdbFavoriteDao().queryBuilder();
        List<DbJoke> favJokesConcreteCategory = queryBuilderJoke.join(queryBuilderFav).query();



        return getHelper().getdbJokeDao().queryForEq("category_id", getCategoryByName(categoryName).get(0));
    }

    public List<DbJoke> getAllJokes() {
        return getHelper().getdbJokeDao().queryForAll();
    }

    public List<DbCategory> getAllCategory() {
        return getHelper().getdbCategoryDao().queryForAll();
    }

    public List<DbCategory> getCategoryByName(String name) {
        return getHelper().getdbCategoryDao().queryForEq("name", name);
    }

//    public List<dbFavorite> getAllFavoriteJokes() {
//        return getHelper().getdbFavoriteDao().queryForAll();
//    }


    public List<DbJoke> getAllFavoriteJokes() {
        return getHelper().getdbJokeDao().queryForEq("isFav", true);
    }

    public List<DbJoke> getFavoriteJokesByCategory(DbCategory category) throws SQLException {
        QueryBuilder<DbJoke, Integer> queryBuilder = getHelper().getdbJokeDao().queryBuilder();
        queryBuilder.where().eq("category_id", category).and().eq("isFav", true);
        return queryBuilder.query();
    }

    public List<DbJoke> getFavoriteJokesByCategoryName(String categoryName) throws SQLException {
        return getFavoriteJokesByCategory(getCategoryByName(categoryName).get(0));
    }

    public void updateJokeFavColumn(boolean isFav, int jokeId) throws SQLException {
        UpdateBuilder<DbJoke, Integer> updateBuilder = getHelper().getdbJokeDao().updateBuilder();
        updateBuilder.where().eq("id", jokeId);
        updateBuilder.updateColumnValue("isFav", isFav);
        updateBuilder.update();
    }

    public DbJoke getJokeById(int id) {
        return getHelper().getdbJokeDao().queryForId(id);
    }

    //TODO
    public void fillDatabase() {
        DbCategory c1 = new DbCategory("O Jasiu", R.drawable.boy);
        DbCategory c2 = new DbCategory("O informatykach", R.drawable.programmer);
        DbCategory c3 = new DbCategory("O zwierzetach", R.drawable.animal);
        DbCategory c4 = new DbCategory("Szkolne", R.drawable.school);
        DbCategory c5 = new DbCategory("Rozne", R.drawable.smile);
        databaseManager.addCategory(c1);
        databaseManager.addCategory(c2);
        databaseManager.addCategory(c3);
        databaseManager.addCategory(c4);
        databaseManager.addCategory(c5);


        databaseManager.addJoke(new DbJoke("W zoo:\n" +
                "Tato dlaczego ta gorylica jest poza klatka? - pyta Jasiu.\n" +
                "Jasiu jestesmy dopiero przy kasie!", c1));

        databaseManager.addJoke(new DbJoke("Tata probuje zmobilizowac Jasia do nauki.\n" +
                "- Jasiu, jak dostaniesz piatke to dam ci 10 zlotych.\n" +
                "Nastepnego dnia, w szkole, Jasiu podchodzi do nauczycielki i szepcze:\n" +
                "- Prosze pani, chce pani latwo zarobic piec zlotych?", c1));

        databaseManager.addJoke(new DbJoke("Jasiu wymien piec zwierzat zyjących w Afryce - mowi nauczycielka.\n" +
                "- Dwie malpy i trzy slonie", c1));

        databaseManager.addJoke(new DbJoke("Na urodzinach informatyka znajomy daje mu prezent. Otwiera, patrzy, a tam pendrive. Po chwili mowi:\n" +
                "- Dziekuje za pamiec.", c2));

        databaseManager.addJoke(new DbJoke("Programista mowi do kolegi: \n" +
                "- Popracuj dzis sam nad tym naszym programem, bo ja musze byc na urodzinach mamy, konczy dzis 64 lata! \n" +
                "- Ooo, to musisz isc koniecznie! To taka piekna, okragla rocznica.", c2));

        databaseManager.addJoke(new DbJoke("Facet rozmawia przez telefon:\n" +
                "- Halo! Towarzystwo ochrony zwierzat?\n" +
                "- Tak, slucham.\n" +
                "- Na drzewie siedzi listonosz i drazni mojego psa...", c3));

        databaseManager.addJoke(new DbJoke("Ida dwa koty przez pustynie.\n" +
                "Jeden nagle mowi:\n" +
                "-Ej, stary. Nie ogarniam tej kuwety.", c3));

        databaseManager.addJoke(new DbJoke("Przed egzaminem student pyta studenta: \n" +
                "- Powtarzales cos? \n" +
                "- Ta. \n" +
                "- A co? \n" +
                "- Bedzie dobrze, bedzie dobrze!", c4));
    }
}
