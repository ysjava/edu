package com.sandgrains.edu.learnmodel;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MyUtils {

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void executeInThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void test(int[] nums, int k) {
        k %= nums.length;
        int right = k % nums.length;
        int temp;
        int temp2 = nums[0];
        boolean[] visited = new boolean[nums.length];

        for (int i = 0; i < nums.length; i++) {
            temp = nums[right];
            nums[right] = temp2;

            if (visited[(right + k) % nums.length]) {
                temp2 = nums[right + 1];
                visited[right] = true;
                right = (right + k + 1) % nums.length;
            } else {
                temp2 = temp;
                visited[right] = true;
                right = (right + k) % nums.length;
                Arrays.sort(nums);
            }
        }

    }

}
