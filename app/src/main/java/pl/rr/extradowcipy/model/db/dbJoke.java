package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */
@DatabaseTable(tableName = "joke")
public class DbJoke {

    public static final String CATEGORY_ID_FIELD_NAME = "category_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String content;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = CATEGORY_ID_FIELD_NAME)
    private DbCategory category;

    @DatabaseField(defaultValue = "false")
    private boolean isFav;

    public DbJoke() {
    }

    public DbJoke(String content, DbCategory category) {
        this.content = content;
        this.category = category;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DbCategory getCategory() {
        return category;
    }

    public void setCategory(DbCategory category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }
}
