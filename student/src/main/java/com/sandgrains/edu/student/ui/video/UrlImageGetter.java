package com.sandgrains.edu.student.ui.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.util.XPopupUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sandgrains.edu.student.utils.custom.HtmlTextView;

public class UrlImageGetter implements Html.ImageGetter {
    Context c;
    HtmlTextView container;

    public UrlImageGetter(HtmlTextView t, Context c) {
        this.c = c;
        this.container = t;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        ImageLoader.getInstance().loadImage(source,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        Log.e("HGASFasdjhAF","onLoadingComplete");
                        loadedImage = calculateBitmapSize(loadedImage);
                        urlDrawable.bitmap = loadedImage;
                        urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
                                loadedImage.getHeight());
                        container.invalidate();
                        container.setText(container.getText());
                    }
                });

        return urlDrawable;
    }

    public static class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    private Bitmap calculateBitmapSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int tvWidth = container.getWidth();

        float scale;
        if (width > height) {
            int newWidth = Math.min(width, tvWidth);
            scale = (float) newWidth / height;
        } else {
            int oneInThirdScreenHeight = XPopupUtils.getScreenHeight(c) / 3;
            int newHeight = Math.min(height, oneInThirdScreenHeight);
            scale = (float) newHeight / height;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }
}