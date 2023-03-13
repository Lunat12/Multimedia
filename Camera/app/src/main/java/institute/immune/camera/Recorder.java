package institute.immune.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Recorder extends AppCompatActivity {

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    File newfile;
    Button stop, play, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        start = findViewById(R.id.btn_record);
        stop = findViewById(R.id.btn_stop);
        play = findViewById(R.id.btn_play);

        stop.setEnabled(false);
        start.setEnabled(true);
        play.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Recorder.this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);


                } else {

                    startRecording();
                    stop.setEnabled(true);
                    start.setEnabled(false);
                    play.setEnabled(false);
                    showMessage(getString(R.string.recording));
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
                stop.setEnabled(false);
                start.setEnabled(true);
                play.setEnabled(true);
                showMessage(getString(R.string.recorder_stopped));
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setEnabled(false);
                start.setEnabled(false);
                play.setEnabled(false);

                playRecording();
                showMessage(getString(R.string.playing_audio));
            }
        });

        Button btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Recorder.this,Menu.class));

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0].equals(Manifest.permission.RECORD_AUDIO)) {
                    startRecording();
                    showMessage(getString(R.string.recording));
                } else {
                    showMessage(getString(R.string.recording_permissions));
                }
                return;
        }
    }


    private void startRecording(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        File path = null;

        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                path = getExternalFilesDir(Environment.DIRECTORY_RECORDINGS);
            }
            else{
                path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }

            newfile = File.createTempFile(getString(R.string.temp_audio), getString(R.string.gp), path);
            mediaRecorder.setOutputFile(newfile.getAbsolutePath());
            mediaRecorder.prepare();
        }
        catch(IOException e){
            Log.e(getString(R.string.io), e.getMessage());
        }

        mediaRecorder.start();

    }

    private void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
    }

    private void playRecording(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer){
                showMessage(getString(R.string.audio_ended));
                stop.setEnabled(false);
                start.setEnabled(true);
                play.setEnabled(true);
                }
        });
        try{
            mediaPlayer.setDataSource(newfile.getAbsolutePath());
            mediaPlayer.prepare();
        }
        catch(IOException e){
            Log.e(getString(R.string.io), e.getMessage());
        }

        mediaPlayer.start();
    }

    private void showMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}