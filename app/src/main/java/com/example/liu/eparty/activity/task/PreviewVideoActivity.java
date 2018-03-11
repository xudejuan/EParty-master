package com.example.liu.eparty.activity.task;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.liu.eparty.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class PreviewVideoActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.replay)
    ImageButton imageButton;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_video);
        init();
    }

    private void init() {
        filePath = getIntent().getStringExtra("videoPath");
        try {
            playVideo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void playVideo() throws IllegalArgumentException,
            IllegalStateException, IOException {
        Uri uri = Uri.parse(filePath);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                imageButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.replay)
    public void replay() {
        imageButton.setVisibility(View.GONE);
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        if (videoView.isPlaying()) {
            videoView.pause();
            videoView = null;
        }
        super.onDestroy();
    }

}
