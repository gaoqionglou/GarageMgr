package com.wyr.garage;

import android.app.Application;
import android.content.Context;

import com.wyr.garage.data.model.Garage;
import com.wyr.garage.db.AppDatabase;

public class AppApplication extends Application {
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        initData();
    }

    public static Context getContext() {
        return mApplication;
    }

    private void initData() {

    }
}
