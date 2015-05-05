package pl.rr.extradowcipy.ui.navdrawer;

/**
 * Created by Rafal on 2015-04-21.
 */
public class NDItemChild {

    private String childName;
    private int counter;

    public NDItemChild(int counter, String childName) {
        this.counter = counter;
        this.childName = childName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
