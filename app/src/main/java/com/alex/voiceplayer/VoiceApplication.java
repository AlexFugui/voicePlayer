package com.alex.voiceplayer;

import android.app.Application;

import com.alex.voice.SPlayer;


public class VoiceApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPlayer.init(this);
        SPlayer.instance()
                .useWakeMode(false)//是否使用唤醒锁,默认不使用
                .useWifiLock(false)//是否使用wifi锁,默认不使用
                .setUseCache(false);//是否使用缓存,默认开启
    }
}
