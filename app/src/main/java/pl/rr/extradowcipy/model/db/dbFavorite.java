package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */

@DatabaseTable(tableName = "favorite")
public class DbFavorite {

        public static final String JOKE_ID_FIELD_NAME = "joke_id";

        @DatabaseField(generatedId = true)
        private int id;

        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = JOKE_ID_FIELD_NAME)
        private DbJoke joke;


        public DbFavorite() {
        }

        public DbFavorite(DbJoke joke) {
            this.joke = joke;

        }

        public DbJoke getJoke() {
            return joke;
        }

        public void setJoke(DbJoke joke) {
            this.joke = joke;
        }
}


