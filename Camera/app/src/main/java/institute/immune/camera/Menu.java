package institute.immune.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView btnPhoto = findViewById(R.id.camera);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(Menu.this,ImageCapture.class));

            }
        });

        ImageView btnRecorder = findViewById(R.id.recorder);
        btnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(Menu.this,Recorder.class));

            }
        });

        ImageView btnVideoGallery = findViewById(R.id.video_gallery);
        btnVideoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(Menu.this,VideoPlayer.class));

            }
        });

        ImageView btnImageGallery = findViewById(R.id.image_gallery);
        btnImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(Menu.this,ImageGallery.class));

            }
        });
    }
}