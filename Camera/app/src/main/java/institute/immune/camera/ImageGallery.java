package institute.immune.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ImageGallery extends AppCompatActivity {

    private File path;
    private static Bitmap currentImg;


    public static void setCurrentImg(Bitmap currentImg) {
        ImageGallery.currentImg = currentImg;
    }

    public static Bitmap getCurrentImg() {
        return currentImg;
    }

    LinearLayout layout, currentBox;

    private Gallery gallery_frame;
    private PictureDisplay display_frame;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        gallery_frame = new Gallery();
        display_frame = new PictureDisplay();





    }

    public void changeFragment(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(currentImg != null){
            fragmentTransaction.replace(R.id.fragmentContainer, display_frame);
        }
        else{
            fragmentTransaction.replace(R.id.fragmentContainer, gallery_frame);

        }

        fragmentTransaction.commit();
    }




}