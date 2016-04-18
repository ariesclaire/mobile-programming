package helloworld.example.com.hwlists;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Activity shows the list
 */
public class ListActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    ListView ls;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActivityStacks.getInstance().addActivity(this);

        ls = (ListView) findViewById(R.id.list);
        ArrayList<ListItem> items = new ArrayList<>();
        ListItem table = new ListItem(R.drawable.table, "Time Table");
        ListItem tip = new ListItem(R.drawable.tip, "Tip Calculator");
        ListItem cal = new ListItem(R.drawable.calcul, "Calculator");
        ListItem pw = new ListItem(R.drawable.pw, "Change Pwd");
        items.add(table);
        items.add(tip);
        items.add(cal);
        items.add(pw);

        ListviewAdapter adapter = new ListviewAdapter(this, R.layout.item_view, items);
        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ListActivity.this, TimeTable.class));
                        break;
                    case 1:
                        startActivity(new Intent(ListActivity.this, TipCalculator.class));
                        break;
                    case 2:
                        Intent c = new Intent(ListActivity.this, Calculator.class);
                        Bundle myData = new Bundle();
                        c.putExtras(myData);
                        startActivityForResult(c, 101);
                        break;
                    case 3:
                        startActivity(new Intent(ListActivity.this, ChangePw.class));
                        break;
                }
            }
        });
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if ((requestCode == 101) && (resultCode == Activity.RESULT_OK)) {
                Float val = data.getExtras().getFloat("val");
                Toast.makeText(getApplicationContext(), "Calculated result is " + val, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error occured getting result from 'Calculator'", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        long tempTIme = System.currentTimeMillis();
        long intervalTime = tempTIme - backPressedTime;

        //intervalTime안에 두번 누르면 모든 액티비티 종료
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            ActivityStacks.getInstance().finishAllActivity();
            super.onBackPressed();
        }
        //back키 누르면 토스트 띄워줌
        else {
            backPressedTime = tempTIme;
            Toast.makeText(getApplicationContext(), "Press the 'Back' button to exit", Toast.LENGTH_SHORT).show();
        }

    }

}
