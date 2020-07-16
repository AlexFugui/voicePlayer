package com.alex.voiceplayer;

import android.app.Application;

import com.alex.voice.VoicePlayer;


public class VoiceApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VoicePlayer.init(this);
    }
}
