package helloworld.example.com.hwlists;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple calculator
 */
public class Calculator extends AppCompatActivity {

    EditText result;
    int[] n = {R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4, R.id.n5, R.id.n6,
            R.id.n7, R.id.n8, R.id.n9};
    Button[] num = new Button[10]; //number buttons(0~9)
    int[] o = {R.id.div, R.id.sum, R.id.sub, R.id.mul};
    Button[] op = new Button[4]; //operators(/,+,-,*)
    Button cancel, fin; //c,=
    String last, lastop = "";
    float f1, f2;   //numbers displayed in the result area

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        result = (EditText) findViewById(R.id.result);
        result.setFocusableInTouchMode(false);
        last = result.getText().toString();

        for (int i = 0; i < n.length; i++) {
            num[i] = (Button) findViewById(n[i]);
            /*number button click event handler*/
            num[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < n.length; i++) {
                        //if a number is inserted
                        if (v.getId() == num[i].getId()) {
                            //if there is no value in the result area or the value is 0
                            if (result.getText().toString().equals("") || result.getText().toString().equals("0"))
                                result.setText(num[i].getText().toString());
                            else
                                result.setText(result.getText() + num[i].getText().toString());

                            last = num[i].getText().toString();
                        }
                    }
                }
            });
        }

        for (int i = 0; i < o.length; i++) {
            op[i] = (Button) findViewById(o[i]);
            /*operator click event handler*/
            op[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < o.length; i++) {
                        if (v.getId() == op[i].getId()) {
                            /*error detect - toast appropriate error messages*/
                            //if there is no number inserted before
                            if (result.getText().toString().equals(""))
                                Toast.makeText(getApplicationContext(), "숫자를 먼저 입력하세요.", Toast.LENGTH_SHORT).show();
                                //if an operator inserted right before
                            else if (last.equals("+") || last.equals("-") || last.equals("*") || last.equals("/"))
                                Toast.makeText(getApplicationContext(), "올바르지 않은 수식입니다.", Toast.LENGTH_SHORT).show();

                                //no error - compute
                            else {
                                //if there is only one number before
                                if (lastop.equals("")) {
                                    f1 = Float.parseFloat(result.getText().toString());
                                    //set the result as 'result += operator'
                                    result.setText("" + f1 + op[i].getText().toString());
                                }
                                //if the result before was 'number + operator + number' format
                                else {
                                    float compute = 0;
                                    f2 = Float.parseFloat(result.getText().toString().substring(result.getText().toString().indexOf(lastop) + 1));
                                    switch (lastop) {
                                        case "+":
                                            compute = f1 + f2;
                                            break;
                                        case "-":
                                            compute = f1 - f2;
                                            break;
                                        case "*":
                                            compute = f1 * f2;
                                            break;
                                        case "/":
                                            compute = f1 / f2;
                                            break;
                                        default:
                                            break;
                                    }
                                    f1 = compute;
                                    //set the result as 'computed result + operator'
                                    result.setText(compute + op[i].getText().toString());
                                }
                                last = lastop = op[i].getText().toString();
                            }
                        }
                    }
                }
            });

            fin = (Button) findViewById(R.id.fin);
            fin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == fin.getId()) {
                        /*error detection*/
                        //if there is only a number
                        if (lastop.equals("")) {
                            //do nothing
                            result.setText(result.getText().toString());
                        }
                        //if there is no number inserted before
                        else if (result.getText().toString().equals(""))
                            Toast.makeText(getApplicationContext(), "숫자를 먼저 입력하세요.", Toast.LENGTH_SHORT).show();
                            //if the last inserted value was an operator
                        else if (last.equals("+") || last.equals("-") || last.equals("*") || last.equals("/"))
                            Toast.makeText(getApplicationContext(), "완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show();

                            //no error - compute
                        else {
                            float compute = 0;
                            f2 = Float.parseFloat(result.getText().toString().substring(result.getText().toString().indexOf(lastop) + 1));
                            switch (lastop) {
                                case "+":
                                    compute = f1 + f2;
                                    break;
                                case "-":
                                    compute = f1 - f2;
                                    break;
                                case "*":
                                    compute = f1 * f2;
                                    break;
                                case "/":
                                    compute = f1 / f2;
                                    break;
                                default:
                                    break;
                            }
                            Intent local = new Intent();
                            local.putExtra("val", compute);
                            setResult(Activity.RESULT_OK, local);
                            finish();
                            result.setText("" + compute);
                            lastop = "";
                        }
                    }
                }
            });
            cancel = (Button) findViewById(R.id.c);
            //cancel button event handler - clear all
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == cancel.getId()) {
                        result.setText("");
                        last = lastop = "";
                    }
                }
            });
        }
    }

}
