package com.sandgrains.edu.student.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import static android.view.View.OVER_SCROLL_ALWAYS;
import static android.view.View.OVER_SCROLL_IF_CONTENT_SCROLLS;
import static android.view.View.OVER_SCROLL_NEVER;

/**
 * @author A lonely cat
 */
public class ViewPager2Util {

    /**
     * Change to OVER_SCROLL_NEVER Mode
     *
     * @param viewPager2 v
     * @return ViewPager2
     */
    public static ViewPager2 changeToNeverMode(ViewPager2 viewPager2) {
        return changeOverScrollMode(viewPager2, OVER_SCROLL_NEVER);
    }

    /**
     * Change to OVER_SCROLL_ALWAYS Mode
     *
     * @param viewPager2 v
     * @return ViewPager2
     */
    public static ViewPager2 changeToAlwaysMode(ViewPager2 viewPager2) {
        return changeOverScrollMode(viewPager2, OVER_SCROLL_ALWAYS);
    }

    /**
     * Change to OVER_SCROLL_IF_CONTENT_SCROLLS Mode
     *
     * @param viewPager2 v
     * @return ViewPager2
     */
    public static ViewPager2 changeToIfContentScrollsMode(ViewPager2 viewPager2) {
        return changeOverScrollMode(viewPager2, OVER_SCROLL_IF_CONTENT_SCROLLS);
    }

    /**
     * Change OverScrollMode
     *
     * @param viewPager2 v
     * @param overMode   o
     */
    public static ViewPager2 changeOverScrollMode(ViewPager2 viewPager2, int overMode) {
        View childView = viewPager2.getChildAt(0);
        if (childView instanceof RecyclerView) childView.setOverScrollMode(overMode);
        return viewPager2;
    }
}
