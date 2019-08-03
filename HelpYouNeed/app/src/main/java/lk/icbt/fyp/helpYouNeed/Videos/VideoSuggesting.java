package lk.icbt.fyp.helpYouNeed.Videos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import lk.icbt.fyp.helpYouNeed.R;


public class VideoSuggesting extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_suggesting);
        getSupportActionBar().hide();
        videoView = (VideoView) findViewById(R.id.videoViewId);

        mediaController = new MediaController(this);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.nature);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.nature11);

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.nature);
            }
        });

    }
}
