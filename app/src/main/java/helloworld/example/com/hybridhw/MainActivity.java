package helloworld.example.com.hybridhw;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView browser = (WebView) findViewById(R.id.webView1);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        browser.loadUrl("file:///android_asset/local.html");
    }


    /**
     * JavaScriptInterface
     */
    public class JavaScriptInterface {
        Context mContext;
        String cur="";

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        /**
         * Number button handler
         * @param num
         * @return
         */
        @JavascriptInterface
        public String update(String num) {
            cur += num;
            return cur;
        }

        /**
         * SMS handler
         * send sms using intent and show result using toast
         * @param smsNumber
         * @param smsText
         */
        @JavascriptInterface
        public void send(String smsNumber, String smsText) {
            PendingIntent sentIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, new Intent("SMS_SENT_ACTION"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, new Intent("SMS_DELIVERED_ACTION"), 0);

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            // 전송 성공
                            Toast.makeText(mContext, "전송 완료", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            // 전송 실패
                            Toast.makeText(mContext, "전송 실패", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            // 서비스 지역 아님
                            Toast.makeText(mContext, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            // 무선 꺼짐
                            Toast.makeText(mContext, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            // PDU 실패
                            Toast.makeText(mContext, "PDU Null", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter("SMS_SENT_ACTION"));

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            // 도착 완료
                            Toast.makeText(mContext, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            // 도착 안됨
                            Toast.makeText(mContext, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter("SMS_DELIVERED_ACTION"));

            SmsManager mSmsManager = SmsManager.getDefault();
            mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
        }

    }
}
