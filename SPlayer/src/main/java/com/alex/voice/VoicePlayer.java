package com.alex.voice;

import android.app.Application;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.alex.voice.cache.VoiceCacheUtils;
import com.alex.voice.listener.PlayerListener;
import com.alex.voice.netWork.VoiceDownloadUtil;

import java.io.IOException;

public class VoicePlayer {
    private static MediaPlayer mediaPlayer;
    private volatile static VoicePlayer voicePlayer;

    public VoicePlayer() {
    }

    public static void init(Application context) {
        VoiceCacheUtils.init(context);
    }

    public static VoicePlayer instance() {
        if (voicePlayer == null) {
            synchronized (VoicePlayer.class) {
                if (voicePlayer == null) {
                    voicePlayer = new VoicePlayer();
                }
            }
        }
        return voicePlayer;
    }

    public void playByUrl(final String url, final PlayerListener listener) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    mediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            String cacheUrl = VoiceCacheUtils.instance().hasCache(url);
            if (cacheUrl == null) {
//                new AudioAsyncTask(VoiceCacheUtils.instance()).execute(url);
                new Thread() {
                    @Override
                    public void run() {
                        VoiceDownloadUtil.instance().download(url);
                    }
                }.start();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                        listener.Loading(mediaPlayer, i);
                    }
                });
            } else {
                //使用缓存
                mediaPlayer.setDataSource(cacheUrl);
                mediaPlayer.prepare();
                listener.Loading(mediaPlayer, 100);
            }
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            listener.onError(e);
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                listener.LoadSuccess(mediaPlayer);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                listener.onCompletion(mediaPlayer);
            }
        });
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer != null ? mediaPlayer : new MediaPlayer();
    }

    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void reset() {
        mediaPlayer.reset();
    }

    public void release() {
        mediaPlayer.release();
    }

    public boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        } else {
            return mediaPlayer.isPlaying();
        }
    }

    public void setCacheDirPath(String dirPath) {
        VoiceCacheUtils.instance().setCacheDirPath(dirPath);
    }

    public void setCachePath(String path) {
        VoiceCacheUtils.instance().setCachePath(path);
    }
}
