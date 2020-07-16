package com.alex.voice.listener;

import com.alex.voice.player.SMediaPlayer;

public interface PlayerListener {
    void LoadSuccess(SMediaPlayer mediaPlayer);

    void Loading(SMediaPlayer mediaPlayer, int i);

    void onCompletion(SMediaPlayer mediaPlayer);

    void onError(Exception e);
}
