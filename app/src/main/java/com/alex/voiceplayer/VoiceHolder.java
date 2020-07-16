package com.alex.voiceplayer;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.alex.voice.listener.OnDownloadListener;
import com.alex.voice.netWork.VoiceDownloadUtil;

import java.io.File;

public class VoiceHolder extends BaseHolder<String> {
    Button button;

    public VoiceHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull String data, final int position) {
        button = itemView.findViewById(R.id.btn);
        VoiceDownloadUtil.instance().download(data, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                button.setText("加载完成");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnViewClickListener.onViewClick(button, position);
                    }
                });
            }

            @Override
            public void onDownloading(int progress) {
                button.setText(progress + "%");
            }

            @Override
            public void onDownloadFailed(Exception e) {
                button.setText("加载失败" + e.toString());
            }
        });
    }
}
