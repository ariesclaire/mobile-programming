package helloworld.example.com.hwlists;

/**
 * Created by Owner on 2016-04-16.
 * Class for data of list item
 */
public class ListItem {
    private int img;
    private String txt;

    /**
     * Constructor
     *
     * @param img
     * @param txt
     */
    public ListItem(int img, String txt) {
        this.img = img;
        this.txt = txt;
    }

    /**
     * @return
     */
    public int getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(int img) {
        this.img = img;
    }

    /**
     * @return
     */
    public String getTxt() {
        return txt;
    }

    /**
     * @param txt
     */
    public void setTxt(String txt) {
        this.txt = txt;
    }
}
