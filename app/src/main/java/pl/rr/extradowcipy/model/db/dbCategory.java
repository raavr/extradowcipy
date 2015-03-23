package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */
@DatabaseTable(tableName = "category")
public class dbCategory {


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    dbCategory() {

    }

    public dbCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
