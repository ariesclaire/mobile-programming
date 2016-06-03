package helloworld.example.com.maze;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Owner on 2016-06-03.
 */
public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    Canvas cacheCanvas;
    Path path;
    Bitmap backBuffer;
    int width, height, clientHeight;
    private Bitmap road, wall, user, end; //image
    private Bitmap scaledRoad, scaledWall, scaledUser, send; //adjust image size
    private int imgWidth, imgHeight; //img size
    private int screenWidth, screenHeight; //screen size
    private int numOfWidthImg, numOfHeightImg; //number of images
    private boolean[][] isRoad; //true : road, false: wall
    private Paint mPaint;
    Context context;
    SurfaceHolder mHolder;
    int endX = 5, endY = 10;

    //constructor
    public DrawingSurface(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    //initialize
    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        //bring resource
        Resources res = getResources();
        road = BitmapFactory.decodeResource(res, R.mipmap.road);
        wall = BitmapFactory.decodeResource(res, R.mipmap.wall);
        user = BitmapFactory.decodeResource(res, R.mipmap.user);
        end = BitmapFactory.decodeResource(res, R.mipmap.end);

        //get window size
        screenWidth = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        screenHeight = ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

        numOfWidthImg = 10;
        numOfHeightImg = 13;
        //width of each cell
        imgWidth = (int) screenWidth / numOfWidthImg;
        //height of each cell
        imgHeight = (int) screenHeight / numOfHeightImg;

        //scaling
        scaledRoad = Bitmap.createScaledBitmap(road, imgWidth, imgHeight, false);
        scaledWall = Bitmap.createScaledBitmap(wall, imgWidth, imgHeight, false);
        scaledUser = Bitmap.createScaledBitmap(user, imgWidth, imgHeight, false);
        send = Bitmap.createScaledBitmap(end, imgWidth, imgHeight / 2, false);

        //initialize map
        initMap();

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    //create surface
    public void surfaceCreated(SurfaceHolder holder) {
        width = getWidth();
        height = getHeight();

        //canvas info
        cacheCanvas = new Canvas();
        backBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); //back buffer

        cacheCanvas.setBitmap(backBuffer);
        cacheCanvas.drawColor(Color.WHITE);

        //paint info
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        for (int i = 0; i < numOfWidthImg; i++) {
            for (int j = 0; j < numOfHeightImg; j++) {

                if (isRoad[i][j] == true) { //road.
                    cacheCanvas.drawBitmap(scaledRoad, i * imgWidth, j * imgHeight, mPaint);
                } else { //wall
                    cacheCanvas.drawBitmap(scaledWall, i * imgWidth, j * imgHeight, mPaint);
                }
            }
        }
        cacheCanvas.drawBitmap(scaledUser, 0, 0, mPaint);
        cacheCanvas.drawBitmap(send, endX * imgWidth, endY * imgHeight, mPaint);

        draw();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    int lastX, lastY, currX, currY;
    int userX = 0, userY = 0;
    boolean isDeleting;

    @Override
    public boolean onTouchEvent(MotionEvent event) { //when invoke touch event.
        super.onTouchEvent(event);
        int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDeleting) break;
                for (int i = 0; i < numOfWidthImg; i++) {
                    for (int j = 0; j < numOfHeightImg; j++) {
                        //check position
                        if (currX >= i * imgWidth && currX < (i + 1) * imgWidth && currY >= j * imgHeight && currY < (j + 1) * imgHeight) {
                            if (isRoad[i][j] == true) { //road
                                currX = (int) event.getX();
                                currY = (int) event.getY();
                                cacheCanvas.drawLine(lastX, lastY, currX, currY, mPaint);
                                lastX = currX;
                                lastY = currY;
                                userX = i * imgWidth;
                                userY = j * imgHeight;
                            } else { //wall
                                userX = 0;
                                userY = 0;
                                currX = currY = 0;
                                cacheCanvas.drawLine(lastX, lastY, currX, currY, mPaint);
                                draw(cacheCanvas);
                                invalidate();
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDeleting) isDeleting = false;
                draw(cacheCanvas);
                invalidate();
                cacheCanvas.drawBitmap(scaledUser, userX, userY, mPaint);
                if(userX == endX * imgWidth && userY >= endY * imgHeight) {
                    Intent i = new Intent(getContext(),SpeedPopup.class);
                    context.startActivity(i);
                    init();
                    userX = 0;
                    userY = 0;
                    currX = currY = 0;
                    cacheCanvas.drawLine(lastX, lastY, currX, currY, mPaint);
                    draw(cacheCanvas);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isDeleting = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        draw();
        return true;
    }

    protected void draw() {
        Canvas canvas = null;
        try {
            canvas = mHolder.lockCanvas(null); //upload to screen buffer from back buffer

            for (int i = 0; i < numOfWidthImg; i++) {
                for (int j = 0; j < numOfHeightImg; j++) {
                    if (isRoad[i][j] == true) { //road.
                        canvas.drawBitmap(scaledRoad, i * imgWidth, j * imgHeight, mPaint);
                    } else { //wall
                        canvas.drawBitmap(scaledWall, i * imgWidth, j * imgHeight, mPaint);
                    }
                    if (i == endX && j == endY)
                        canvas.drawBitmap(send, i * imgWidth, j * imgHeight, mPaint);

                }
            }
            canvas.drawBitmap(backBuffer, 0, 0, mPaint); //draw line
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (mHolder != null) mHolder.unlockCanvasAndPost(canvas);
        }
    }

    //draw initial map
    private boolean[][] initMap() {
        //initialize road
        isRoad = new boolean[numOfWidthImg][numOfHeightImg];

        for (int i = 0; i < numOfWidthImg; i++) {
            for (int j = 0; j < numOfHeightImg; j++) {
                isRoad[i][j] = true; //initialize all true
            }
        }

        //create wall
        for (int i = 1; i < numOfWidthImg; i++) {
            for (int j = 0; j < 2; j++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 6)
                    isRoad[i][j] = false;
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 3; j < numOfHeightImg; j++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 6)
                    isRoad[i][j] = false;
            }
        }
        for (int i = 6; i < numOfWidthImg; i++) {
            for (int j = 3; j < numOfHeightImg; j++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 6)
                    isRoad[i][j] = false;
            }
        }

        return isRoad;
    }
}

