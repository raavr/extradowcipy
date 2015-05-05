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

import pl.rr.extradowcipy.model.db.DbCategory;
import pl.rr.extradowcipy.model.db.DbFavorite;
import pl.rr.extradowcipy.model.db.DbJoke;

/**
 * Created by Rafal on 2014-11-12.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final boolean DEBUG = false;
    private static final String DATABASE_NAME = "ExtraDowcipy.db";
    private static final int DATABASE_VERSION = 1;

    // the DAO objects we use to access the db tables
    private Dao<DbCategory, Integer> dbCategoryDao = null;
    private RuntimeExceptionDao<DbCategory, Integer> dbCategoryRuntimeDao = null;
    private Dao<DbJoke, Integer> dbJokeDao = null;
    private RuntimeExceptionDao<DbJoke, Integer> dbJokeRuntimeDao = null;
    private Dao<DbFavorite, Integer> dbFavoriteDao = null;
    private RuntimeExceptionDao<DbFavorite, Integer> dbFavoriteRuntimeDao = null;

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
            TableUtils.createTable(connectionSource, DbCategory.class);
            TableUtils.createTable(connectionSource, DbFavorite.class);
            TableUtils.createTable(connectionSource, DbJoke.class);
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
            TableUtils.dropTable(connectionSource, DbCategory.class, true);
            TableUtils.dropTable(connectionSource, DbJoke.class, true);
            TableUtils.dropTable(connectionSource, DbFavorite.class, true);
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
    public Dao<DbCategory, Integer> getCategoryDao() throws SQLException {
        if (dbCategoryDao == null) {
            dbCategoryDao = getDao(DbCategory.class);
        }
        return dbCategoryDao;
    }

    public Dao<DbJoke, Integer> getJokeDao() throws SQLException {
        if (dbJokeDao == null) {
            dbJokeDao = getDao(DbJoke.class);
        }
        return dbJokeDao;
    }

    public Dao<DbFavorite, Integer> getFavoriteDao() throws SQLException {
        if (dbFavoriteDao == null) {
            dbFavoriteDao = getDao(DbFavorite.class);
        }
        return dbFavoriteDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our dbArtist class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<DbCategory, Integer> getdbCategoryDao() {
        if (dbCategoryRuntimeDao == null) {
            dbCategoryRuntimeDao = getRuntimeExceptionDao(DbCategory.class);
        }
        return dbCategoryRuntimeDao;
    }

    public RuntimeExceptionDao<DbJoke, Integer> getdbJokeDao() {
        if (dbJokeRuntimeDao == null) {
            dbJokeRuntimeDao = getRuntimeExceptionDao(DbJoke.class);
        }
        return dbJokeRuntimeDao;
    }

    public RuntimeExceptionDao<DbFavorite, Integer> getdbFavoriteDao() {
        if (dbFavoriteRuntimeDao == null) {
            dbFavoriteRuntimeDao = getRuntimeExceptionDao(DbFavorite.class);
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
        dbCategoryRuntimeDao = null;
    }
}

