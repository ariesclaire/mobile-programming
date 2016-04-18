package helloworld.example.com.hwlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 2016-04-16.
 * Custom ListViewAdater extends BaseAdapter
 */
public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ListItem> data;
    private int layout;

    /**
     * Constructor
     *
     * @param context
     * @param layout
     * @param data
     */
    public ListviewAdapter(Context context, int layout, ArrayList<ListItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return data.get(position).getTxt();
    }

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        ListItem listviewitem = data.get(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.img);
        icon.setImageResource(listviewitem.getImg());
        TextView name = (TextView) convertView.findViewById(R.id.txt);
        name.setText(listviewitem.getTxt());
        return convertView;
    }
}