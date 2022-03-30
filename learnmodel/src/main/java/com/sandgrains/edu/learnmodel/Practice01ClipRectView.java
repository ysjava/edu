package com.sandgrains.edu.learnmodel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


public class Practice01ClipRectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree = 0;
    int degree2 = 0;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 45, 0);
    ObjectAnimator animator2 = ObjectAnimator.ofInt(this, "degree2", 0, -45, 0);
    AnimatorSet animationSet = new AnimatorSet();

    public Practice01ClipRectView(Context context) {
        super(context);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator2.setDuration(2000);
        animator2.setInterpolator(new LinearInterpolator());
//        animator2.setRepeatCount(ValueAnimator.INFINITE);
//        animator2.setRepeatMode(ValueAnimator.REVERSE);

        animationSet.playSequentially(animator, animator2);
        animationSet.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    public void setDegree2(int degree2) {
        this.degree2 = degree2;
        invalidate();
    }


    public void setTest() {
        animationSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;


        // 第二遍绘制：下半部分
        canvas.save();

        if (degree < 90) {
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        } else {
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        camera.save();
        camera.rotateX(degree);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // 第一遍绘制：上半部分
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);

        camera.save();
        camera.rotateX(degree2);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
//        int bitmapWidth = bitmap.getWidth();
//        int bitmapHeight = bitmap.getHeight();
//        int centerX = getWidth() / 2;
//        int centerY = getHeight() / 2;
//        int x = centerX - bitmapWidth / 2;
//        int y = centerY - bitmapHeight / 2;
//
//        canvas.save();
//
//        camera.save();
//        camera.rotateX(degree);
//        canvas.translate(centerX, centerY);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-centerX, -centerY);
//        camera.restore();
//
//        canvas.drawBitmap(bitmap, x, y, paint);
//        canvas.restore();
    }
}
