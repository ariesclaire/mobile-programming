package helloworld.example.com.bbokbbok;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Owner on 2016-06-03.
 */
public class DrawImage extends View {

    private	Paint mPaint;
    private	Bitmap Bbok1;	//unbroken
    private	Bitmap Bbok2;	//broken
    private Bitmap scaledBbok1, scaledBbok2; //scaled bubble
    private int bubbleWidth, bubbleHeight; //bubble size
    private int screenWidth, screenHeight; //screen size
    private int numOfWidthBub, numOfHeightBub; //number of bubbles
    private boolean[][] canBroken; //true : can broken, false: already broken

    //constructor
    public DrawImage(Context c) {
        super(c);
        init();
    }

    public DrawImage(Context c, AttributeSet a) {
        super(c, a);
        init();
    }

    //initialize function
    public	void init()	{
        mPaint	= new Paint();
        Resources res = getResources();
        //bring resource
        Bbok1 = BitmapFactory.decodeResource(res, R.mipmap.b1);
        Bbok2 =	BitmapFactory.decodeResource(res, R.mipmap.b2);

        //get window size
        screenWidth = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        screenHeight = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

        //initialize the number of bubbles
        numOfWidthBub = 6;
        numOfHeightBub = 8;

        //width of each bubble
        bubbleWidth = (int)screenWidth / numOfWidthBub;
        //height of each bubble
        bubbleHeight = (int)screenHeight / numOfHeightBub;

        //scaling
        scaledBbok1 = Bitmap.createScaledBitmap(Bbok1,bubbleWidth,bubbleHeight,false);
        scaledBbok2 = Bitmap.createScaledBitmap(Bbok2,bubbleWidth,bubbleHeight,false);

        //initialize state of bubbles
        canBroken = new boolean[numOfWidthBub][numOfHeightBub];
        for(int i =0; i < numOfWidthBub;i++){
            for(int j =0; j < numOfHeightBub;j++){
                canBroken[i][j] = true; //initially, all the bubbles can be broken
            }
        }

    }

    public	boolean	onTouchEvent(MotionEvent event)	{
        //receive x,y
        float x = event.getX();
        float y = event.getY();

        if	(event.getAction()	==	MotionEvent.ACTION_UP)	{
            for(int i=0;i<numOfWidthBub;i++) {
                for(int j=0;j<numOfHeightBub;j++){
                    //check position
                    if(x >= i*bubbleWidth && x < (i+1)*bubbleWidth && y >= j*bubbleHeight && y < (j+1)*bubbleHeight){
                        canBroken[i][j] = false; //if it is already broken, it cannot be broken.
                    }
                }
            }
            invalidate();
        }

        return	true;
    }	//	end	of onTouchEvent

    //draw image
    protected void onDraw(Canvas canvas)	{
        canvas.save();
        for(int i =0; i < numOfWidthBub;i++){
            for(int j =0; j < numOfHeightBub;j++){

                if(canBroken[i][j] == true){ //not broken.
                    canvas.drawBitmap(scaledBbok1, i*bubbleWidth, j*bubbleHeight, mPaint);
                    canvas.restore();
                }
                else{ //broken
                    canvas.drawBitmap(scaledBbok2,i*bubbleWidth, j*bubbleHeight , mPaint);
                    canvas.restore();
                }
            }
        }

    }
}

