package com.sandgrains.edu.learnmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ScrollView extends androidx.appcompat.widget.AppCompatTextView {

private Scroller scroller;

    public ScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }


    private int lastX = 0;
    private int lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

//        int cx = (int) (event.getX()>=getRight()?getRight():event.getX());
//        int cy = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
//                Log.e("JHgsfw","aa: "+ event.getRawX());
//                Log.e("JHgsfw","bb: "+ getScrollX());

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                Log.e("JHgsfw","log3: "+ getScrollX());
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                scroller.startScroll(getScrollX(),0,50,0);
                invalidate();
                break;
            default:
                break;
        }

        lastX = x;
        lastY = y;

        return true;
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}
