package com.sandgrains.edu.learnmodel;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.InputStream;

public class HtmlTextView extends androidx.appcompat.widget.AppCompatTextView {
    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html) {
        Html.ImageGetter imgGetter;

        imgGetter = new UrlImageGetter(this, getContext());


        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, null));

        // make links work
        setMovementMethod(LinkMovementMethod.getInstance());
        setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable,null));
    }
}
