package com.bspl.qscanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bspl.qscanner.Adapter.AllimgAdopter;
import com.bspl.qscanner.Adapter.PDFAdapter;
import com.bspl.qscanner.extraclass.ClickListener;
import com.bspl.qscanner.extraclass.PrograssDilogbox;

import com.bspl.qscanner.extraclass.popuobox;
import com.bspl.qscanner.extraclass.showToast;
import com.bspl.qscanner.modelclass.Imagefromgallery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;

import static android.os.Environment.getExternalStorageDirectory;


public class AllImagefrommobile extends AppCompatActivity implements ClickListener {

    private AllimgAdopter adopter;
    ArrayList<Imagefromgallery> imagearray = new ArrayList<>();
    File[] listFile;
    RecyclerView recyclerView;
    ArrayList arrayList = null;
    ArrayList pdfarrayList = null;
    ArrayList delete = null;
    String b = "";
    ArrayList seclectimage = null;
    FloatingActionButton imagetopdfconverter, imagedelete;
    PrograssDilogbox prograssDilogbox;
    private static final int SELECTED_PIC = 1;
     ArrayList<File> fileList = new ArrayList<File>();
    boolean back = false;
    String flage=null;
    File dir;
    ///
    String ImageDecode;
    String[] FILE;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_imagefrommobile);
        imagetopdfconverter = findViewById(R.id.fab);

        prograssDilogbox = new PrograssDilogbox(AllImagefrommobile.this, R.style.CustomDialog);

        pdfarrayList = new ArrayList();
        seclectimage = new ArrayList();
        delete = new ArrayList();
        recyclerView = findViewById(R.id.PhoneImageGrid);
        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
         flage=bundle.getString("flage");
        if (flage.equals("gallery")) {
            Log.e("flag", "flage");
            prograssDilogbox.show();
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"/");
            getfile( dir);

//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
        } else {
          dir=  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QScanner");
          prograssDilogbox.show();
            getfile( dir);

        }
        imagetopdfconverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuobox p = new popuobox();
                prograssDilogbox.show();
                if (adopter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adopter.getSelected().size(); i++) {
                        stringBuilder.append(adopter.getSelected().get(i).getImagename());
                        seclectimage.add(adopter.getSelected().get(i).getImagename());
                        stringBuilder.append("\n");
                        Log.e("selerfd", adopter.getSelected().get(i).getImagename());
                    }
                    showToast.show(stringBuilder.toString().trim(), AllImagefrommobile.this);
                    p.popuobox(AllImagefrommobile.this, seclectimage, getResources());


                    prograssDilogbox.dismiss();

                } else {
                    showToast.show("No Selection", AllImagefrommobile.this);
                    prograssDilogbox.dismiss();

                }
            }
        });
    }

//    public void getFromSdcard() {
//        prograssDilogbox.show();
//        imagearray = new ArrayList<>();
//
//        arrayList = new ArrayList<>();
//        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QScanner");
//        if (dir.exists()) {
//
//            listFile = dir.listFiles();
//            if (listFile != null) {
//
//                for (int i = 0; i < listFile.length; i++) {
//
//                    arrayList.add(listFile[i].getAbsolutePath());
//
//                }
//                Log.e("Size", "" + arrayList.size());
//                for (int k = 0; k <= arrayList.size() - 1; k++) {
//                    Imagefromgallery imagearra = new Imagefromgallery();
//
//                    b = arrayList.get(k).toString();
//                    imagearra.setImagename(b);
//
//                    imagearray.add(imagearra);
//                }
//                adopter = new AllimgAdopter(AllImagefrommobile.this, imagearray);
//                recyclerView.setAdapter(adopter);
//                prograssDilogbox.dismiss();
//                adopter.notifyDataSetChanged();
//            } else {
//                prograssDilogbox.dismiss();
//
//                showToast.show(" Image not Found", AllImagefrommobile.this);
//            }
//        }
//
//    }

    public void getfile(File dir) {
        prograssDilogbox.show();
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                    prograssDilogbox.show();

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".jpg")||listFile[i].getName().endsWith(".jpeg")||listFile[i].getName().endsWith(".png")) {

                        for (int j = 0; j < fileList.size(); j++) {
                            prograssDilogbox.show();
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {

                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);
                             prograssDilogbox.show();
                        }
                    }
                }
            }
        }
        for(int k=0;k<=fileList.size()-1;k++){
            Imagefromgallery imagearra = new Imagefromgallery();

                    b = fileList.get(k).toString();
                    imagearra.setImagename(b);
                    imagearra.setSelected(false);
                    imagearray.add(imagearra);
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Collections.sort(imagearray, Collections.reverseOrder());

        adopter = new AllimgAdopter(AllImagefrommobile.this, imagearray);
                recyclerView.setAdapter(adopter);
                prograssDilogbox.dismiss();
                adopter.notifyDataSetChanged();
       // return fileList;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }
}