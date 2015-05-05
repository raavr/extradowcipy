package pl.rr.extradowcipy.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import pl.rr.extradowcipy.NDItemChild;
import pl.rr.extradowcipy.NDItemGroup;
import pl.rr.extradowcipy.R;

/**
 * Created by Rafal on 2015-04-21.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<NDItemGroup> groups;
    private OnChildItemClickListener mListener;

    public ExpandableListAdapter(Context context, List<NDItemGroup> groups, OnChildItemClickListener listener) {
        this.context = context;
        this.groups = groups;
        mListener = listener;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.nd_item, parent, false);
        }

        NDItemGroup ndItemGroup = groups.get(groupPosition);
        ((RobotoTextView) convertView.findViewById(R.id.navigation_title)).setText(ndItemGroup.getGroupName());
        ((ImageView) convertView.findViewById(R.id.item_icon)).setImageResource(ndItemGroup.getResourceId());
        if(ndItemGroup.getResourceId() == R.drawable.ic_action_favorite) {
            ImageView epandCollapseIv = (ImageView) convertView.findViewById(R.id.nd_item_expand);
            epandCollapseIv.setVisibility(View.VISIBLE);
            epandCollapseIv.setImageResource(isExpanded ? R.drawable.ic_action_collapse: R.drawable.ic_action_expand);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.nd_item_child, parent, false);
        }
        NDItemChild ndItemChild = (NDItemChild) getChild(groupPosition, childPosition);
        final String categoryName = ndItemChild.getChildName();
        ((RobotoTextView) convertView.findViewById(R.id.nd_child_title)).setText(categoryName);
        ((TextView) convertView.findViewById(R.id.nd_child_counter)).setText(Integer.toString(ndItemChild.getCounter()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onChildItemClick(categoryName);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnChildItemClickListener {
        public void onChildItemClick(String categoryName);
    }
}
