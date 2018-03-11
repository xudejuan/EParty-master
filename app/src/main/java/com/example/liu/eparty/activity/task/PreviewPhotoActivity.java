package com.example.liu.eparty.activity.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.liu.eparty.R;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class PreviewPhotoActivity extends AppCompatActivity {

    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        init();
    }

    private void init() {
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            downloadPicture(url);
        }else {
            photoView.setImageBitmap((Bitmap) getIntent().getParcelableExtra("bitmap"));
        }
    }

    public void downloadPicture(final String url) {
//        Glide.with(this).load(url).into(photoView);
    }
}
