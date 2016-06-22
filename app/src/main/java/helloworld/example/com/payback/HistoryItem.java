package helloworld.example.com.payback;

import android.graphics.drawable.Drawable;

/**
 * Created by sunghun on 2016. 6. 21..
 */
public class HistoryItem {

    private String[] mData;

    /**
     * True if this item is selectable
     */
    private boolean mSelectable = true;

    /**
     * Initialize with icon and data array
     *
     * @param icon
     * @param obj
     */
    public HistoryItem(Drawable icon, String[] obj) {
        mData = obj;
    }

    /**
     * Initialize with icon and strings
     *
     * @param obj01
     * @param obj02
     * @param obj03
     */
    public HistoryItem(String obj01, String obj02, String obj03, String obj04, String obj05, String obj06, String obj07) {
        mData = new String[7];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        mData[3] = obj04;
        mData[4] = obj05;
        mData[5] = obj06;
        mData[6] = obj07;
    }

    /**
     * True if this item is selectable
     */
    public boolean isSelectable() {
        return mSelectable;
    }

    /**
     * Set selectable flag
     */
    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    /**
     * Get data array
     *
     * @return
     */
    public String[] getData() {
        return mData;
    }

    /**
     * Get data
     */
    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    /**
     * Set data array
     *
     * @param obj
     */
    public void setData(String[] obj) {
        mData = obj;
    }


    /**
     * Compare with the input object
     *
     * @param other
     * @return
     */

}