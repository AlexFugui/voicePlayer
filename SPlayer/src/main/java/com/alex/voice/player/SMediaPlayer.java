package com.alex.voice.player;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 重写MediaPlayer,更稳定的获取播放状态
 */
public class SMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {
    public enum Status {
        IDLE    //闲置
        , INITIALIZED   //资源设置好
        , STARTED   //开始
        , PAUSED    //暂停
        , STOPPED   //停止
        , COMPLETED //播放完成
    }

    //记录状态
    private Status mState;

    private OnCompletionListener mOnCompletionListener;

    public SMediaPlayer() {
        super();
        setState(Status.IDLE);
        super.setOnCompletionListener(this);
    }

    @Override
    public void reset() {
        super.reset();
        setState(Status.IDLE);
    }

    @Override
    public void setDataSource(String path) throws IOException {
        super.setDataSource(path);
        setState(Status.INITIALIZED);
    }

    @Override
    public void start() {
        super.start();
        setState(Status.STARTED);
    }

    @Override
    public void pause() {
        super.pause();
        setState(Status.PAUSED);
    }

    @Override
    public void stop() {
        super.stop();
        setState(Status.STOPPED);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mState = Status.COMPLETED;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mediaPlayer);
        }
    }

    public void setState(Status mState) {
        this.mState = mState;
    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == Status.COMPLETED;
    }

    public boolean isPlaying() {
        return mState == Status.STARTED;
    }
}
