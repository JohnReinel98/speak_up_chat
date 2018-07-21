package com.sendbird.android.sample.main;


import android.app.Application;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.utils.PreferenceUtils;

public class BaseApplication extends Application {

    //private static final Stri ng APP_ID = "9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23"; // US-1 Demo
    //private static final String APP_ID = "B951ECFF-5070-46EC-AEBD-7BD387483E4F";
    private static final String APP_ID = "";
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }
}
