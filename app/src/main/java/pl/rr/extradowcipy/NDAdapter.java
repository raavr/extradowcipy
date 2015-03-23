package pl.rr.extradowcipy;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rafal on 2014-10-20.
 */
public class NDAdapter extends ArrayAdapter<NDItem> {
    private Context context;

    public NDAdapter(Context context, int resource, List<NDItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;
       // if(!getItem(position).isHeader()) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.nd_item, null);
            textView = (TextView) convertView.findViewById(R.id.navigation_title);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.item_icon);
            imageView.setBackgroundResource(getItem(position).getIcon());
            if (getItem(position).getIcon() == R.drawable.ic_action_favorite) {
                TextView textCount = (TextView) convertView.findViewById(R.id.item_counter);
                textCount.setText(Integer.toString(getItem(position).getCounter()));
                textCount.setVisibility(View.VISIBLE);
            }
        //}
//        } else {
//            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.nd_item_header, null);
//            textView = (TextView) convertView.findViewById(R.id.header);
//
//        }
        textView.setText(getItem(position).getTitle());
        return convertView;

    }
}
