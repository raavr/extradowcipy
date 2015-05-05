package pl.rr.extradowcipy;

import java.util.ArrayList;

/**
 * Created by Rafal on 2015-04-21.
 */
public class NDItemGroup {

    private String groupName;
    private int resourceId;
    private ArrayList<NDItemChild> children = new ArrayList<>();

    public NDItemGroup(String groupName, int resourceId) {
        this.groupName = groupName;
        this.resourceId = resourceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public ArrayList<NDItemChild> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<NDItemChild> children) {
        this.children = children;
    }
}
