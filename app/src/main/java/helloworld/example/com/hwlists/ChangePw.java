package helloworld.example.com.hwlists;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Activity for changing password
 * Check new password twice
 * <p>
 * Offer crypt with MD5
 * Use SharedPreference
 * Reuse layout_main
 */
public class ChangePw extends AppCompatActivity {

    private TextView notice;
    private EditText pw[] = new EditText[4];
    private int[] pwId = {R.id.pw1, R.id.pw2, R.id.pw3, R.id.pw4};
    private Button btn[] = new Button[12];
    private int[] btnId = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn, R.id.btn0, R.id.btnX};
    private static MessageDigest md;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;
    private int count = 0;
    private String first = "";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityStacks.getInstance().addActivity(this);
        notice = (TextView) findViewById(R.id.notice);


        for (int i = 0; i < pwId.length; i++) {
            pw[i] = (EditText) findViewById(pwId[i]);
            pw[i].setFocusableInTouchMode(false);
        }
        for (int i = 0; i < btnId.length; i++) {
            btn[i] = (Button) findViewById(btnId[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < btnId.length; j++) {
                        if (v.getId() == btn[j].getId() && j != 9) {
                            if (j == 11) {
                                if (count > 0)
                                    pw[--count].setText("");
                            } else {
                                if (count < 4)
                                    pw[count++].setText(btn[j].getText().toString());
                            }

                            if (count == 4) {

                                String password = "";
                                for (int i = 0; i < 4; i++) {
                                    password += pw[i].getText().toString();
                                }
                                Log.d(" ChangePw ", first + " " + password);
                                //처음 입력
                                if (first.equals("")) {
                                    first = password;
                                    for (int i = 0; i < 4; i++) {
                                        pw[i].setText("");
                                    }
                                    count = 0;
                                    notice.setText("Re-enter your password.");
                                }
                                //첫번째와 두번째 입력값 비교
                                //일치시 패스워드 변경
                                //작업 끝낸 후 이전화면으로 돌아감
                                else if (password.equals(first)) {
                                    sh_Pref = getSharedPreferences("myPreference", Context.MODE_MULTI_PROCESS);
                                    if (sh_Pref != null && sh_Pref.contains("password")) {
                                        toEdit = sh_Pref.edit();
                                        toEdit.putString("password", CryptWithMD5.cryptWithMD5(password));
                                        toEdit.commit();
                                        Log.d(" ChangePw ", " password " + sh_Pref.getString("password", CryptWithMD5.cryptWithMD5("0000")));

                                    }
                                    for (int i = 0; i < 4; i++) {
                                        pw[i].setText("");
                                    }
                                    count = 0;
                                    startActivity(new Intent(ChangePw.this, ListActivity.class));
                                }
                                //불일치시 리셋
                                else {
                                    notice.setText("The password you have entered is incorrect.\nPlease start over.");
                                    first = "";
                                    for (int i = 0; i < 4; i++) {
                                        pw[i].setText("");
                                    }
                                    count = 0;
                                }


                            }
                        }
                    }
                }
            });
        }
    }

}
