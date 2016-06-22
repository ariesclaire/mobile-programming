package helloworld.example.com.payback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Account extends AppCompatActivity {

    EditText e1, e2;
    Button bt;

    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sh_Pref = getSharedPreferences("myPreference", Context.MODE_MULTI_PROCESS);
                if (sh_Pref != null && sh_Pref.contains("account")) {
                    toEdit = sh_Pref.edit();
                    toEdit.putString("account", e1.getText() + " " + e2.getText());
                    toEdit.commit();
                }
                startActivity(new Intent(Account.this, MainActivity.class));

            }
        });

    }
}
