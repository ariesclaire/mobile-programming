package helloworld.example.com.payback;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sunghun on 2016. 6. 21..
 */
public class HistoryView extends LinearLayout {

    private TextView mText01;
    private TextView mText02;
    private TextView mText03;
    private TextView mText04;
    private TextView mText05;
    private TextView mText06;
    private TextView mText07;

    public HistoryView (Context context, HistoryItem aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.history_listitem, this, true);

        // Set Text 01 Name
        mText01 = (TextView) findViewById(R.id.name);
        mText01.setText(aItem.getData(0));

        // Set Text 02 Good
        mText02 = (TextView) findViewById(R.id.Good);
        mText02.setText(aItem.getData(1));

        // Set Text 03 Gvalue
        mText03 = (TextView) findViewById(R.id.GoodValue);
        mText03.setText(aItem.getData(2));

        // Set Text 04 Normal
        mText04 = (TextView) findViewById(R.id.Normal);
        mText04.setText(aItem.getData(3));

        // Set Text 05 Nvalue
        mText05 = (TextView) findViewById(R.id.NormalValue);
        mText05.setText(aItem.getData(4));

        // Set Text 06 Bad
        mText06 = (TextView) findViewById(R.id.Bad);
        mText06.setText(aItem.getData(5));

        // Set Text 07 Bvalue
        mText07 = (TextView) findViewById(R.id.BadValue);
        mText07.setText(aItem.getData(6));

    }
    /**
     * set Text
     *
     * @param index
     * @param data
     */
    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        } else if (index == 1) {
            mText02.setText(data);
        } else if (index == 2) {
            mText03.setText(data);
        } else if (index == 3) {
            mText04.setText(data);
        }else if (index == 4) {
            mText05.setText(data);
        } else if (index == 5) {
            mText06.setText(data);
        } else if (index == 6) {
            mText07.setText(data);
        }  else {
            throw new IllegalArgumentException();
        }
    }


}