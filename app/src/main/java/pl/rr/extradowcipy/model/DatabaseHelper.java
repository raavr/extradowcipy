package pl.rr.extradowcipy.model;

/**
 * Created by Rafal on 2015-03-23.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.rr.extradowcipy.model.db.dbCategory;
import pl.rr.extradowcipy.model.db.dbFavorite;
import pl.rr.extradowcipy.model.db.dbJoke;

/**
 * Created by Rafal on 2014-11-12.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final boolean DEBUG = false;
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "ExtraDowcipy.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the dbArtist table
    private Dao<dbCategory, Integer> dbCategoryDao = null;
    private RuntimeExceptionDao<dbCategory, Integer> dbCateogryRuntimeDao = null;
    private Dao<dbJoke, Integer> dbJokeDao = null;
    private RuntimeExceptionDao<dbJoke, Integer> dbJokeRuntimeDao = null;
    private Dao<dbFavorite, Integer> dbFavoriteDao = null;
    private RuntimeExceptionDao<dbFavorite, Integer> dbFavoriteRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            if(DEBUG) Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, dbCategory.class);
            TableUtils.createTable(connectionSource, dbFavorite.class);
            TableUtils.createTable(connectionSource, dbJoke.class);
        } catch (SQLException e) {
            if(DEBUG) Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }




    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(DEBUG) Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, dbCategory.class, true);
            TableUtils.dropTable(connectionSource, dbJoke.class, true);
            TableUtils.dropTable(connectionSource, dbFavorite.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            if(DEBUG) Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our dbArtist class. It will create it or just give the cached
     * value.
     */
    public Dao<dbCategory, Integer> getCategoryDao() throws SQLException {
        if (dbCategoryDao == null) {
            dbCategoryDao = getDao(dbCategory.class);
        }
        return dbCategoryDao;
    }

    public Dao<dbJoke, Integer> getJokeDao() throws SQLException {
        if (dbJokeDao == null) {
            dbJokeDao = getDao(dbJoke.class);
        }
        return dbJokeDao;
    }

    public Dao<dbFavorite, Integer> getFavoriteDao() throws SQLException {
        if (dbFavoriteDao == null) {
            dbFavoriteDao = getDao(dbFavorite.class);
        }
        return dbFavoriteDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our dbArtist class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<dbCategory, Integer> getdbCategoryDao() {
        if (dbCateogryRuntimeDao == null) {
            dbCateogryRuntimeDao = getRuntimeExceptionDao(dbCategory.class);
        }
        return dbCateogryRuntimeDao;
    }

    public RuntimeExceptionDao<dbJoke, Integer> getdbJokeDao() {
        if (dbJokeRuntimeDao == null) {
            dbJokeRuntimeDao = getRuntimeExceptionDao(dbJoke.class);
        }
        return dbJokeRuntimeDao;
    }

    public RuntimeExceptionDao<dbFavorite, Integer> getdbFavoriteDao() {
        if (dbFavoriteRuntimeDao == null) {
            dbFavoriteRuntimeDao = getRuntimeExceptionDao(dbFavorite.class);
        }
        return dbFavoriteRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        dbFavoriteDao = null;
        dbJokeDao = null;
        dbJokeDao = null;
        dbJokeRuntimeDao = null;
        dbFavoriteRuntimeDao = null;
        dbCateogryRuntimeDao = null;
    }
}

