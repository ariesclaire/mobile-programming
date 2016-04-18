package helloworld.example.com.hwlists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple Tip Calculator
 */
public class TipCalculator extends AppCompatActivity {

    EditText input, otherCost;
    RadioGroup rGroup;
    RadioButton but15, but20, other;
    TextView result;
    Button submit;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        input = (EditText) findViewById(R.id.input);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        but15 = (RadioButton) findViewById(R.id.but15);
        but20 = (RadioButton) findViewById(R.id.but20);
        other = (RadioButton) findViewById(R.id.other);
        otherCost = (EditText) findViewById(R.id.otherCost);
        result = (TextView) findViewById(R.id.result);
        submit = (Button) findViewById(R.id.submit);

        //if radioButton 'other' is clicked focus editText 'otherCost'
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherCost.setVisibility(View.VISIBLE);
                otherCost.requestFocus();
            }
        });

        //calculate button click listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if input is null
                if (input.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "금액을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                float cost = Float.parseFloat(input.getText().toString());
                double tip = 0.0;
                //if input is negative
                if (cost < 0) {
                    Toast.makeText(getApplicationContext(), "올바르지 않은 금액입니다. 다시 한번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (other.isChecked()) {
                    float p = Float.parseFloat(otherCost.getText().toString()) / 100;
                    tip = cost * p;
                } else if (but15.isChecked())
                    tip = cost * 0.15;
                else if (but20.isChecked())
                    tip = cost * 0.2;

                    //if rate is not selected
                else {
                    Toast.makeText(getApplicationContext(), "비율을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                tip = Math.round(tip * 100d) / 100d;
                result.setText("Tip : " + tip + "\nTotal : " + Math.round((cost + tip)) * 100d / 100d);
            }
        });
    }

}
