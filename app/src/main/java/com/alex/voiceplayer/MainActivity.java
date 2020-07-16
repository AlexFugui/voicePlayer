package com.alex.voiceplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alex.voice.VoicePlayer;
import com.alex.voice.listener.PlayerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> localList = new ArrayList<>();

    VoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //rv的holder中默认使用了下载功能,可以自己关闭,删除缓存后点击播放再下载
        RecyclerView recyclerView = findViewById(R.id.rv);
        Button btn = findViewById(R.id.btn);
        //资源可能失效,请自己更换
        localList.add("http://dj.cqcstny.com/huiyuan/201807/27/20180727190011fb98cf3a8bf63e88_7685.mp3");
        localList.add("http://img.maituichina.com/2020/1/2020151579482802840gbpXGb.mp3");
        localList.add("http://stdj.60dj.com/huiyuan/201806/25/201806251429463117d87cf4f00fb1_37213.mp3");
        localList.add("http://stdj.60dj.com/huiyuan/201806/15/201806152148362f25326e4e246ddd_37213.mp3");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new VoiceAdapter(localList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<String>() {
            @Override
            public void onItemClick(@NonNull View view, int viewType, @NonNull String data, int position) {
                if (view.getId() == R.id.btn) {
                    final Button btn = findViewById(R.id.btn);
                    VoicePlayer.instance().playByUrl(data, new PlayerListener() {
                        @Override
                        public void LoadSuccess(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }

                        @Override
                        public void Loading(MediaPlayer mediaPlayer, int i) {
                            btn.setText(i + "%");
                            if (i == 100) {
                                btn.setText("完成");
                            }
                        }

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            btn.setText("播放完成");
                        }

                        @Override
                        public void onError(Exception e) {
                            btn.setText("播放异常" + e.toString());
                        }

                    });
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VoicePlayer.instance().isPlaying()) {
                    //播放暂停 默认播放第一个item中内容
                    VoicePlayer.instance().playByUrl(localList.get(0), new PlayerListener() {
                        @Override
                        public void LoadSuccess(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }

                        @Override
                        public void Loading(MediaPlayer mediaPlayer, int i) {
                            Toast.makeText(MainActivity.this, "加载进度:" + i + "%", Toast.LENGTH_SHORT).show();
                            if (i == 100) {
                                Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(MainActivity.this, "播放异常" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    VoicePlayer.instance().getMediaPlayer().pause();
                }
            }
        });
    }
}