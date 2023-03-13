package institute.immune.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gallery extends Fragment {

    private LinearLayout layout, currentBox;
    private File path;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gallery.
     */
    // TODO: Rename and change types and number of parameters
    public static Gallery newInstance(String param1, String param2) {
        Gallery fragment = new Gallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        path = getActivity().getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        ArrayList<String> pictures = getPaths(path.toString());

        layout = view.findViewById(R.id.linearLayoutMain);


        for(int i=0; i < pictures.size(); i++){
            Bitmap bitmap = BitmapFactory.decodeFile(path + getString(R.string.bar)  + pictures.get(i));
            generateImage(bitmap, i, getActivity().getApplicationContext());

        }

        Button btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageGallery) getActivity()).finish();
                startActivity(new Intent(((ImageGallery) getActivity()),Menu.class));

            }
        });



        return view;
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

    public void generateImage(Bitmap bitmap, int index, Context context) {

        if(index % 2 == 0){
            currentBox = new LinearLayout(context);
            LinearLayout.LayoutParams boxParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            currentBox.setLayoutParams(boxParameters);
            boxParameters.setMargins(0,10,0,10);
            boxParameters.setLayoutDirection(LinearLayout.HORIZONTAL);
            layout.addView(currentBox);

        }


        ImageView image = new ImageView(context);
        LinearLayout.LayoutParams contentParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);


        image.setLayoutParams(contentParameters);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int crop = (width - height) / 2;

        Bitmap cropImg =  Bitmap.createBitmap(bitmap, crop, 0, height, height);
        image.setImageBitmap(cropImg);
        image.isClickable();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageGallery.setCurrentImg(bitmap);
                ((ImageGallery) getActivity()).changeFragment();

            }
        });

        contentParameters.width = 500;
        contentParameters.height = 500;

        currentBox.addView(image);
    }
}