package com.bspl.qscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bspl.qscanner.extraclass.showToast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class Textscanner extends AppCompatActivity {
    private static final String TAG = "mainPage";
    private static final int requestPermissionID = 101;
    CameraSource cameraSource;
    SurfaceView surfaceView;
    TextView textView;
    String alltext;
    FloatingActionButton nextpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textscanner);
        surfaceView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView);
        nextpage=findViewById(R.id.froword);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if(alltext!=null){
                    ClipData clip = ClipData.newPlainText("scanne text", alltext);
                    clipboard.setPrimaryClip(clip);
                    showToast.show("Copy",Textscanner.this);
                      textView.setText("");
                      alltext=null;

                }else {
                    showToast.show("No text Scanning",Textscanner.this);
                }

            }
        });
        startCameraSource();
    }

    private void startCameraSource(){

         TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

        if(!textRecognizer.isOperational()){
            Log.w(TAG,"Detector dependencies missing.");
            return;
        }
        else{
            cameraSource = new CameraSource.Builder(getApplicationContext(),textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Textscanner.this,
                                new String[] {Manifest.permission.CAMERA},requestPermissionID);
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size()==0)return;

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for(int i=0;i<items.size();++i){
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue()+"\n");
                            }
                            textView.setText(stringBuilder.toString());

                            alltext=stringBuilder.toString();

                        }
                    });
                }
            });


        }
    }
}
