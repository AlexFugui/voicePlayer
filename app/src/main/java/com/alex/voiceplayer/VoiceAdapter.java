package com.alex.voiceplayer;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

public class VoiceAdapter extends DefaultAdapter<String> {
    public VoiceAdapter(List<String> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<String> getHolder(@NonNull View v, int viewType) {
        return new VoiceHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.holder;
    }
}
