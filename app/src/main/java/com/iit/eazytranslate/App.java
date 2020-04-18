package com.iit.eazytranslate;


import android.app.Application;
import android.content.res.Resources;

public class App extends Application {
    private static App sharedInstance;
    private static Resources resources;


    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
        sharedInstance = this;
    }

    public static App getAppInstance() {
        return sharedInstance;
    }

    public static Resources getResourses() {
        return resources;
    }

}
