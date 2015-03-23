package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */

@DatabaseTable(tableName = "favorite")
public class dbFavorite {

        public static final String JOKE_ID_FIELD_NAME = "joke_id";

        @DatabaseField(generatedId = true)
        private int id;

        @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = JOKE_ID_FIELD_NAME)
        private dbJoke joke;


        public dbFavorite() {
        }

        public dbFavorite(dbJoke joke) {
            this.joke = joke;

        }

        public dbJoke getJoke() {
            return joke;
        }

        public void setJoke(dbJoke joke) {
            this.joke = joke;
        }
}


