package helloworld.example.com.payback;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HS.Ju on 2016-05-29.
 */
public class IngListAdapter extends BaseAdapter {

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

    private List<IngItem> mItems = new ArrayList<IngItem>();

    public IngListAdapter(Context context) {
        mContext = context;
    }

    public void addItem(IngItem it) {
        mItems.add(it);
    }


    public void setListItems(List<IngItem> lit) {
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
        IngView itemView;

        if (convertView == null) {
            itemView = new IngView(mContext, mItems.get(position));
        } else {
            itemView = (IngView) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
        }
        ImageButton imgbtn = (ImageButton) itemView.findViewById(R.id.iconItem);
        imgbtn.setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(mContext, messaging.class);
                mContext.startActivity(intent);
            }
        });
        return itemView;
    }

}
