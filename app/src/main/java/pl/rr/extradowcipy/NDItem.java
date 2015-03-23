package pl.rr.extradowcipy;

/**
 * Created by Rafal on 2014-10-20.
 */
public class NDItem  {
    private String title;
    private int icon;
    private int id;
    private int counter;
    private boolean isHeader = false;

    public NDItem(String title, int icon, int id) {
        this.title = title;
        this.icon = icon;
        this.id = id;
    }

    public NDItem(String title) {
        this(title, -1, -1);
        isHeader = true;
    }

    public NDItem(String title, int icon, int id, boolean isHeader) {
        this.title = title;
        this.icon = icon;
        this.id = id;
        this.isHeader = isHeader;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }
}
