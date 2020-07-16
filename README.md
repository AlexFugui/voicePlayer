# VoicePlayer
播放url格式的网络音频，支持缓存.

# 依赖 
```
implementation 'com.alex:SPlayer:1.0.0'
```

# 用法:
详情请下载demo查看


[Gitee地址](https://gitee.com/alexfugui/VoicePlayer "Gitee")
  
[GitHub](https://github.com/AlexFugui/VoicePlayer "GitHub")

# 初始化
```
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPlayer.init(this);
    }
}
```

# 使用
```
//预加载,会下载文件下来
VoiceDownloadUtil.instance()
                .download(data, new OnDownloadListener() {
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
```

```
//普通播放
SPlayer.instance()
        .useWakeMode(false)//是否使用环形锁,默认不使用
        .useWifiLock(false)//是否使用wifi锁,默认不使用
        .setUseCache(true)//是否使用缓存,默认开启
        .playByUrl(localList.get(0), new PlayerListener() {
            @Override
            public void LoadSuccess(SMediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }

            @Override
            public void Loading(SMediaPlayer mediaPlayer, int i) {
                Toast.makeText(MainActivity.this, "加载进度:" + i + "%", Toast.LENGTH_SHORT).show();
                if (i == 100) {
                    Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCompletion(SMediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "播放异常" + e.toString(), Toast.LENGTH_SHORT).show();
            }

        });

```
#权限
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

#升级计划
```
- [x] 重写优化tMediaPlayer
- [ ] 增加边播边缓存功能
- [ ] 添加录音功能
```

