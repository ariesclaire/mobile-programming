package helloworld.example.com.maze;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Owner on 2016-06-03.
 */
public class SpeedPopup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//TITLE바 NONONO.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
//팝업 외부 뿌연 효과
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//뿌연 효과 정도
        layoutParams.dimAmount= 0.7f;
//적용
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_speedpopup);
    }
}
