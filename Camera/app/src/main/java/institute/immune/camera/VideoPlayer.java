package institute.immune.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoPlayer extends AppCompatActivity {

    TextView title;
    int currentVideo;
    ArrayList<VideoObj> videos = new ArrayList<VideoObj>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        VideoView videoHolder = findViewById(R.id.video);
        title = findViewById(R.id.title);

        createVideoList();

        currentVideo = 0;


        videoHolder.setVideoURI(videos.get(currentVideo).getVideo());
        title.setText(videos.get(currentVideo).getTitle());



        videoHolder.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                MediaController mediaController = new MediaController(videoHolder.getContext());
                videoHolder.setMediaController(mediaController);
                mediaController.setAnchorView(videoHolder);
            }
        });

        Button btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(VideoPlayer.this,Menu.class));

            }
        });

        ImageView btnPrev = findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentVideo --;
                if(currentVideo < 0){
                    currentVideo = videos.size() -1;
                }

                videoHolder.setVideoURI(videos.get(currentVideo).getVideo());
                title.setText(videos.get(currentVideo).getTitle());

            }
        });

        ImageView btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentVideo ++;
                if(currentVideo >= videos.size()){
                    currentVideo = 0;
                }

                videoHolder.setVideoURI(videos.get(currentVideo).getVideo());
                title.setText(videos.get(currentVideo).getTitle());

            }
        });


    }

    private void createVideoList(){
        VideoObj newVideo1 = new VideoObj(getString(R.string.space_unicorn), getString(R.string.music_video), getString(R.string.android_resource_path) + getPackageName() + getString(R.string.bar) + R.raw.space_unicorn);
        VideoObj newVideo2 = new VideoObj(getString(R.string.insane), getString(R.string.music_video), getString(R.string.android_resource_path) + getPackageName() + getString(R.string.bar) + R.raw.allastor);
        VideoObj newVideo3 = new VideoObj(getString(R.string.hornet), getString(R.string.music_video), getString(R.string.android_resource_path) + getPackageName() + getString(R.string.bar) + R.raw.hornet);

        videos.add(newVideo1);
        videos.add(newVideo2);
        videos.add(newVideo3);
    }
}