package helloworld.example.com.hwlists;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

/**
 * A simple Time table
 */
public class TimeTable extends AppCompatActivity {

    int[] MON = {R.id.mon1, R.id.mon2, R.id.mon3, R.id.mon4, R.id.mon5, R.id.mon6,
            R.id.mon7, R.id.mon8, R.id.mon9, R.id.mon10};
    int[] TUE = {R.id.tue1, R.id.tue2, R.id.tue3, R.id.tue4, R.id.tue5, R.id.tue6,
            R.id.tue7, R.id.tue8, R.id.tue9, R.id.tue10};
    int[] WED = {R.id.wed1, R.id.wed2, R.id.wed3, R.id.wed4, R.id.wed5, R.id.wed6,
            R.id.wed7, R.id.wed8, R.id.wed9, R.id.wed10};
    int[] THU = {R.id.thu1, R.id.thu2, R.id.thu3, R.id.thu4, R.id.thu5, R.id.thu6,
            R.id.thu7, R.id.thu8, R.id.thu9, R.id.thu10};
    int[] FRI = {R.id.fri1, R.id.fri2, R.id.fri3, R.id.fri4, R.id.fri5, R.id.fri6,
            R.id.fri7, R.id.fri8, R.id.fri9, R.id.fri10};
    TextView[] today = new TextView[10];

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        boolean weekand = false;
        String day = "";
        day = new Date().toString().substring(0, 3);
        if (day.equalsIgnoreCase("MON")) {
            for (int i = 0; i < 10; i++)
                today[i] = (TextView) findViewById(MON[i]);
        } else if (day.equalsIgnoreCase("TUE")) {
            for (int i = 0; i < 10; i++)
                today[i] = (TextView) findViewById(TUE[i]);
        } else if (day.equalsIgnoreCase("WED")) {
            for (int i = 0; i < 10; i++)
                today[i] = (TextView) findViewById(WED[i]);
        } else if (day.equalsIgnoreCase("THU")) {
            for (int i = 0; i < 10; i++)
                today[i] = (TextView) findViewById(THU[i]);
        } else if (day.equalsIgnoreCase("FRI")) {
            for (int i = 0; i < 10; i++)
                today[i] = (TextView) findViewById(FRI[i]);
        } else
            weekand = true;

        //highlight today's timetable if it's weekday
        if (weekand == false)
            for (int i = 0; i < 10; i++)
                today[i].setBackgroundColor(Color.rgb(255, 0, 127));
    }
}