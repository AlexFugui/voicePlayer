package com.alex.voice.cache;

import android.app.Application;

import java.io.File;
import java.util.Objects;

public class VoiceCacheUtils {
    private static VoiceCacheUtils cacheUtils;
    private String cacheDirPath = Objects.requireNonNull(mContext.getExternalCacheDir()).getAbsolutePath();
    private String cachePath = "/VoiceCache";
    private File file;
    private static Application mContext;
    public VoiceCacheUtils() {
        file = new File(cacheDirPath + cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void init(Application context) {
        mContext = context;
    }

    public static VoiceCacheUtils instance() {
        if (cacheUtils == null) {
            synchronized (VoiceCacheUtils.class) {
                if (cacheUtils == null) {
                    cacheUtils = new VoiceCacheUtils();
                }
            }
        }
        return cacheUtils;
    }

    public VoiceCacheUtils setCachePath(String cachePath) {
        this.cachePath = cachePath;
        return this;
    }

    public VoiceCacheUtils setCacheDirPath(String cacheDirPath) {
        this.cacheDirPath = cacheDirPath;
        return this;
    }

    public String getCachePath() {
        return cacheDirPath + cachePath;
    }

    public String hasCache(String url) {
        String fileName;
        fileName = getMediaID(url);
        File tempFile = new File(file, fileName);
        if (tempFile.exists()) {
            return tempFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    public String getMediaID(String url) {
        return url.replaceAll("\\.", "").replaceAll("/", "").replaceAll(":", "") + ".aud";
    }
}
