package com.young.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AppUtil extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

    //定义一个activity列表
    private List<Activity> mList = new ArrayList<Activity>();

    //顶一一个类的实例
    private static AppUtil instance;


    /**
     * 单例模式
     *
     * @return
     */
    public static AppUtil getInstance() {
        return instance;
    }

    /**
     * 如果activity已经 destory了 就移除
     *
     * @param activity
     */
    public void remove(Activity activity) {

        mList.remove(activity);

    }

    /**
     * 添加ativity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 遍历  结束activity  并且退出
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }


}