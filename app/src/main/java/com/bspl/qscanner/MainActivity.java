//package com.bspl.qscanner;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import org.opencv.android.OpenCVLoader;
//import org.opencv.osgi.OpenCVInterface;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.bspl.qscanner.Opencvcode.ImageCropActivity;
//import com.bspl.qscanner.Opencvcode.helpers.MyConstants;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    Button btnOpenGallery;
//    Button btnImageProcess;
//
//    ImageView imageView;
//
//    Uri selectedImage;
//    Bitmap selectedBitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initializeElement();
//        initializeEvent();
//    }
//
//    private void initializeElement() {
//        this.imageView = (ImageView) findViewById(R.id.imageView);
//        this.btnOpenGallery = (Button) findViewById(R.id.btnOpenGallery);
//        this.btnImageProcess = (Button) findViewById(R.id.btnImageProcess);
//    }
//
//    private void initializeEvent() {
//        this.btnOpenGallery.setOnClickListener(this.btnOpenGalleryClick);
//        this.btnImageProcess.setOnClickListener(this.btnImageProcessClick);
//    }
//
//    private View.OnClickListener btnOpenGalleryClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, MyConstants.GALLERY_IMAGE_LOADED);
//        }
//    };
//
//    private View.OnClickListener btnImageProcessClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            //save selected bitmap to our constants
//            //this method will save the image to our device memory
//            //so set this variable to null after the image is no longer used
//            MyConstants.selectedImageBitmap = selectedBitmap;
//
//            //create new intent to start process image
//            Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);
//            startActivity(intent);
//
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MyConstants.GALLERY_IMAGE_LOADED && resultCode == RESULT_OK && data != null) {
//            selectedImage = data.getData();
//            this.loadImage();
//        }
//    }
//
//    private void loadImage() {
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(this.selectedImage);
//            selectedBitmap = BitmapFactory.decodeStream(inputStream);
//            imageView.setImageBitmap(selectedBitmap);
//            btnImageProcess.setVisibility(View.VISIBLE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//}


package com.bspl.qscanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;


import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    String permission[] = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        setContentView(R.layout.activity_main);

        requestPermissions();
    }


    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE

                }, 0);
                Timer timer = new Timer();

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, loginScreen.class));
                        finish();
                    }
                };
                timer.schedule(timerTask, 4000);
            }

        }

    }
}

