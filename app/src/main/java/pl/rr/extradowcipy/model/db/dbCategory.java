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

    @DatabaseField
    private int resId;

    DbCategory() {

    }

    public DbCategory(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public boolean equals(Object o) {
        DbCategory category = (DbCategory) o;
        if(this.getName().equals(category.getName()))
            return true;

        return false;
    }
}
