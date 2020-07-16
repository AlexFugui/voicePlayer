package com.alex.voiceplayer;

import android.app.Application;

import com.alex.voice.SPlayer;


public class VoiceApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPlayer.init(this);
    }
}
