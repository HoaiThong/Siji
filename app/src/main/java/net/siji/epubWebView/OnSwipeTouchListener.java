package net.siji.epubWebView;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private static final int MAX_CLICK_DURATION = 1000;
    private static final int MAX_CLICK_DISTANCE = 15;
    static Context mcontext;
    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private boolean stayedWithinClickDistance;
    View inflationView;




    public  int getLocationx() {
        return Locationx;
    }

    public void setLocationx(int locationx) {
        Locationx = locationx;
    }

    public int Locationx;

    public int getLocationy() {
        return Locationy;
    }

    public void setLocationy(int locationy) {
        Locationy = locationy;
    }

    public int Locationy;

    @SuppressWarnings("static-access")
    public OnSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.mcontext=context;

    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }
    public void newTouch(){

    }

    public boolean onTouch(View v, MotionEvent event) {


        this.inflationView=v;
        gestureDetector.onTouchEvent(event);


        if(event.getAction()== MotionEvent.ACTION_MOVE)
        {
            if (stayedWithinClickDistance && distance(pressedX, pressedY, event.getX(), event.getY()) > MAX_CLICK_DISTANCE) {
                stayedWithinClickDistance = false;
            }
            return true;
        }

        else if (event.getAction()== MotionEvent.ACTION_DOWN) {
            pressStartTime = System.currentTimeMillis();
            pressedX = event.getX();
            pressedY = event.getY();
            stayedWithinClickDistance = true;

            return v.onTouchEvent(event);
        }
        else if(event.getAction()== MotionEvent.ACTION_UP) {

            long pressDuration = System.currentTimeMillis() - pressStartTime;
            if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                newTouch();
            }
            setLocationx((int)event.getX());
            setLocationy((int)event.getY());
            return v.onTouchEvent(event);
        }
        else{
            return v.onTouchEvent(event);
        }

    }
    private static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private static float pxToDp(float px) {
        return px / mcontext.getResources().getDisplayMetrics().density;
    }






    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }



    }

}