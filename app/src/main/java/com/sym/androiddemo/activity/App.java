package com.sym.androiddemo.activity;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by xiaoming on 2016/4/11.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        enableStrictMode();
        LeakCanary.install(this);
    }

    /**
     * 开启严谨模式
     */
    private void enableStrictMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

    }
}
