#[Git Splayer](https://github.com/AlexFugui/voicePlayer)
# SPlayer
播放url格式的网络音频，支持缓存.
 
# 引入
```groovy
implementation 'com.alex:SPlayer:1.0.4'
```

# 用法:
详情请下载demo查看


[Gitee地址](https://gitee.com/alexfugui/VoicePlayer "Gitee")
  
[GitHub](https://github.com/AlexFugui/VoicePlayer "GitHub")

# 初始化
```java
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化是必须的
        SPlayer.init(this);
        //以下不是必须设置
        //corePoolSize 设置核心下载最大线程,默认2 最大6. 传入小于0或大于6不生效
        //maximumPoolSize 设置最大下载线程数量,默认8,小于corePoolSize则等于corePoolSize,最大数值64,根据机器性能自己选择适当的线程数
        SPlayer.instance().setCorePoolSize(int corePoolSize).setMaximumPoolSize(int maximumPoolSize);
        //设置缓存文件夹目录和缓存文件夹名称
        //cacheDirPath 默认为 mContext.getExternalCacheDir()
        //cachePath 默认为 "/VoiceCache"
        SPlayer.instance().setCacheDirPath(String cacheDirPath).setCachePath(String cachePath);
        SPlayer.instance()
                        .useWakeMode(false)//是否使用唤醒锁,默认不使用
                        .useWifiLock(false)//是否使用wifi锁,默认不使用
                        .setUseCache(false);//是否使用缓存,默认开启

    }
}
```

# 使用
```java
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

```java
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
        
String cacheSize = SPlayer.instance().getCacheSize(); //获取缓存大小,已格式化单位

SPlayer.instance().clearCache();//删除缓存

SPlayer.instance().seekTo(int i);//增加播放进度修改

SPlayer.instance().seekTo(long long, MediaPlayer.SEEK_CLOSEST);//适用api28以上的精准定位

//播放assets文件
        SPlayer.instance().playByAsset("overtime.mp3", new PlayerListener() {
            @Override
            public void LoadSuccess(SMediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }

            @Override
            public void Loading(SMediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void onCompletion(SMediaPlayer mediaPlayer) {

            }

            @Override
            public void onError(Exception e) {

            }
        });


```
# 权限
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

# 升级计划
- [x] 重写优化MediaPlayer
- [x] 添加缓存查询和删除
- [ ] 增加边播边缓存功能
- [ ] 添加录音功能

# 测试机型
- [x] 红米K20 pro Android10 MIUI12
- [x] 雷电模拟器3.0 Android7.1.2
- [x] AS自带模拟器 API26

# 更细日志

## v1.0.4
-支持播放assets文件中的音频文件

## v1.0.2
- 增加修改播放进度的方法

## v1.0.1
- 优化MediaPlayer
- 增加缓存查询和删除功能
