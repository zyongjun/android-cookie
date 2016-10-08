package com.joez.dagger;

import android.app.Application;

/**
 * Created by gzzyj on 2016/8/3.
 */
public class CookieApplication extends Application{
    private static CookieApplication mApplication;
    AppComponent mAppComponent;

    public static CookieApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mAppComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
