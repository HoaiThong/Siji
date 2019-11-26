package net.siji.imageSliderViewPager;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Dot {

    private Paint paint;

    private PointF center;

    private int currentRadius;

    public Dot() {
        paint = new Paint();
        paint.setAntiAlias(true);
        center = new PointF();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    public void setCenter(float x, float y) {
        center.set(x, y);
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(int radius) {
        this.currentRadius = radius;
    }

    public void draw(Canvas canvas) {
//        canvas.drawLine(center.x,center.y,center.x+currentRadius,center.y+currentRadius,paint);
        canvas.drawCircle(center.x, center.y, currentRadius, paint);
    }
}
