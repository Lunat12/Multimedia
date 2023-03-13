package institute.immune.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageCapture extends AppCompatActivity {

    private Photo photo_fragment;
    private Video video_fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        photo_fragment = new Photo();
        video_fragment = new Video();

        Button btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ImageCapture.this,Menu.class));

            }
        });

        Button btnPhoto = findViewById(R.id.btn_camera);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(true);

            }
        });

        Button btnVideo = findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(false);

            }
        });

    }

    private void openCamera(boolean isPhoto){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(isPhoto){
            fragmentTransaction.replace(R.id.fragmentContainer, photo_fragment);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
        else{
            fragmentTransaction.replace(R.id.fragmentContainer, video_fragment);
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, 2);

        }

        fragmentTransaction.commit();

    }

    //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){path = getExternalFilesDir(Enviroment.DIRECTORY_RCORDINGS)}else{path = getExternalFilesDir(Enviroment.DIRECTORY_PICTURES)}

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get(getString(R.string.data));
            photo_fragment.setImgView(imgBitmap);
            MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap,new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), getString(R.string.description));
        }
        else if(requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            video_fragment.setVdView(uri);

        }
    }

    public ArrayList<String> getPaths(String path){
        ArrayList<String> photos = new ArrayList<String>();

        File f = new File(path);
        File[] files = f.listFiles();

        for(int i=0; i < files.length; i++){
            File file = files[i];

            if(file.isDirectory()){
                photos.add(file.getName() + "/");
            }
            else{
                photos.add(file.getName());
            }
        }

        return photos;
    }
}