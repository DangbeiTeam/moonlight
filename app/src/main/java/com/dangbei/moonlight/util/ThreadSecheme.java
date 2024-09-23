package com.dangbei.moonlight.util;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 线程调度
 */
public class ThreadSecheme {

    private static Handler uiHandler = new Handler(Looper.getMainLooper());
    private static Handler bgHandler;


    private static void initBgHandle() {
        if (bgHandler == null) {
            HandlerThread bgThread = new HandlerThread("global background thread.");
            bgThread.start();
            bgHandler = new Handler(bgThread.getLooper());
        }
    }


    /**
     * 执行任务-主线程
     *
     * @param runnable
     * @param delayTimeMillSec
     */
    public static void runUITask(Runnable runnable, long delayTimeMillSec) {
        if (runnable == null) {
            return;
        }
        if (delayTimeMillSec < 0) {
            uiHandler.post(runnable);
        } else {
            uiHandler.postDelayed(runnable, delayTimeMillSec);
        }
    }

    /**
     * 执行任务-主线程
     *
     * @param runnable
     */
    public static void runUITask(Runnable runnable) {
        runUITask(runnable, -1);
    }


    /**
     * 执行任务-后台
     *
     * @param task
     * @param delayTimeMillSec
     */
    public static void runBgTask(Runnable task, long delayTimeMillSec) {
        if (task == null) {
            return;
        }
        initBgHandle();
        if (delayTimeMillSec < 0) {
            bgHandler.post(task);
        } else {
            bgHandler.postDelayed(task, delayTimeMillSec);
        }
    }


    /**
     * 执行任务-后台
     *
     * @param task
     */
    public static void runBgTask(Runnable task) {
        runBgTask(task, -1);
    }


    /**
     * 取消任务-后台
     *
     * @param runnable
     */
    public static void cancelBgTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (bgHandler == null) {
            return;
        }
        bgHandler.removeCallbacks(runnable);
    }


    /**
     * 取消任务-主线程
     *
     * @param runnable
     */
    public static void cancelUITask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        //
        uiHandler.removeCallbacks(runnable);
    }
}
