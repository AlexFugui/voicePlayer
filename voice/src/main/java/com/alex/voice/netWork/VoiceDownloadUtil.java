package com.alex.voice.netWork;

import android.util.Log;

import com.alex.voice.cache.VoiceCacheUtils;
import com.alex.voice.listener.OnDownloadListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VoiceDownloadUtil {
    private static VoiceDownloadUtil downloadUtil;
    private OkHttpClient okHttpClient;


    public static VoiceDownloadUtil instance() {
        if (downloadUtil == null) {
            downloadUtil = new VoiceDownloadUtil();
        }
        return downloadUtil;
    }

    public VoiceDownloadUtil() {
        okHttpClient = new OkHttpClient();
    }


    public void download(final String url, final OnDownloadListener listener) {
        String cacheUrl = VoiceCacheUtils.instance().hasCache(url);
        if (cacheUrl == null) {
            Request request = new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    listener.onDownloadFailed(e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    writeFile(url, response, listener);
                }
            });
        } else {
            listener.onDownloading(100);
            listener.onDownloadSuccess(new File(VoiceCacheUtils.instance().getCachePath(), VoiceCacheUtils.instance().getMediaID(url)));
        }

    }

    public void download(final String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("download", "onFailure" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                writeFile(url, response, null);
            }
        });
    }

    private void writeFile(String url, Response response, OnDownloadListener listener) {
        File dir = new File(VoiceCacheUtils.instance().getCachePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, VoiceCacheUtils.instance().getMediaID(url));
        OutputStream outputStream = null;
        InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
        try {
            outputStream = new FileOutputStream(file);
            int len;
            byte[] buffer = new byte[1024 * 10];
            long sum = 0;
            long total = Objects.requireNonNull(response.body()).contentLength();
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                if (listener != null) {
                    listener.onDownloading(progress);
                }
            }
        } catch (IOException e) {
            if (listener != null) {
                listener.onDownloadFailed(e);
            }
        } finally {
            try {
                inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (listener != null) {
                    listener.onDownloadSuccess(file);
                }
            } catch (IOException e) {
                if (listener != null) {
                    listener.onDownloadFailed(e);
                }
            }
        }
    }
}
