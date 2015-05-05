package pl.rr.extradowcipy.model.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Rafal on 2015-03-23.
 */
@DatabaseTable(tableName = "category")
public class DbCategory {


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    DbCategory() {

    }

    public DbCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        DbCategory category = (DbCategory) o;
        if(this.getName().equals(category.getName()))
            return true;

        return false;
    }
}
