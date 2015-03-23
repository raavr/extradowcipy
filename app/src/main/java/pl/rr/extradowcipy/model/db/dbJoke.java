package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */
@DatabaseTable(tableName = "joke")
public class dbJoke {

    public static final String CATEGORY_ID_FIELD_NAME = "category_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String content;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = CATEGORY_ID_FIELD_NAME)
    private dbCategory category;


    public dbJoke() {
    }

    public dbJoke(String content, dbCategory category) {
        this.content = content;
        this.category = category;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public dbCategory getCategory() {
        return category;
    }

    public void setCategory(dbCategory category) {
        this.category = category;
    }
}
