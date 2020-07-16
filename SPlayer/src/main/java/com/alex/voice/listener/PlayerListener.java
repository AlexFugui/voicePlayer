package com.alex.voice.listener;

import android.media.MediaPlayer;

public interface PlayerListener {
    void LoadSuccess(MediaPlayer mediaPlayer);

    void Loading(MediaPlayer mediaPlayer, int i);

    void onCompletion(MediaPlayer mediaPlayer);

    void onError(Exception e);
}
