package helloworld.example.com.payback;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunghun on 2016. 6. 21..
 */
public class HistoryListAdapter extends BaseAdapter {

    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }
    int resourceId ;
    private ListBtnClickListener listBtnClickListener ;


    /**
     * 어댑터 클래스 정의
     *
     *
     */
    private Context mContext;

    private List<HistoryItem> mItems = new ArrayList<HistoryItem>();

    public HistoryListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(HistoryItem it) {
        mItems.add(it);
    }


    public void setListItems(List<HistoryItem> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryView itemView;

        if (convertView == null) {
            itemView = new HistoryView(mContext, mItems.get(position));
        } else {
            itemView = (HistoryView) convertView;

            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
            itemView.setText(3, mItems.get(position).getData(3));
            itemView.setText(4, mItems.get(position).getData(4));
            itemView.setText(5, mItems.get(position).getData(5));
            itemView.setText(6, mItems.get(position).getData(6));
        }
        return itemView;
    }

}