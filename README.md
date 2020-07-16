# VoicePlayer
播放url格式的网络音频，支持缓存.

# 依赖 
```
implementation 'com.alex.voice-player:voice-player:1.0.1'
```

# 用法:
详情请下载demo查看

```
Gitee: 
https://gitee.com/alexfugui/VoicePlayer
```
```
GitHub:
https://github.com/AlexFugui/VoicePlayer
```

```
//需要在application中初始化
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VoicePlayer.init(this);
    }
}
```

```
//预加载
VoiceDownloadUtil.instance().download(url, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                //加载完成
                //如果没有缓存则返回下载完成后的file
                //如果有缓存则直接返回缓存文件的file
            }

            @Override
            public void onDownloading(int progress) {
                //progress是加载进度 0-100
                //如果是加载缓存 直接返回进度100
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //加载失败
            }
        });
```

```
//普通播放
VoicePlayer.instance().playByUrl(url, new PlayerListener() {
                        @Override
                        public void LoadSuccess(MediaPlayer mediaPlayer) {
                            //加载完成,可以在这里播放
                            mediaPlayer.start();
                        }

                        @Override
                        public void Loading(MediaPlayer mediaPlayer, int i) {
                            //可以显示加载进度或播放
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
                            //播放异常后默认触发onCompletion方法
                            Toast.makeText(MainActivity.this, "播放异常" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    });
```

