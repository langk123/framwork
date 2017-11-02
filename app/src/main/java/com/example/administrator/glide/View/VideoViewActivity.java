package com.example.administrator.glide.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.glide.R;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

/**
 * Created by Administrator on 2017/11/2.
 */

public class VideoViewActivity extends AppCompatActivity{
    private IjkVideoView mVideoView;
    private boolean mBackPressed;
    private String mVideoPath = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        mVideoView =  findViewById(R.id.video_view);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.start();
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

}