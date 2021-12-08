package com.bspl.qscanner;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bspl.qscanner.Adapter.PDFAdapter;


import com.bspl.qscanner.extraclass.PrograssDilogbox;
import com.bspl.qscanner.extraclass.popuobox;
import com.bspl.qscanner.extraclass.showToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab,fab1,fab5,fab6;
    private AppBarConfiguration mAppBarConfiguration;
   boolean visivel=false;
   private DrawerLayout drawer;
  private   NavigationView navigationView;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
   private int image []={R.drawable.ic_card_icon_01,R.drawable.buttionshap,R.drawable.ic_fabicon_0};

    ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<File>();
    PDFAdapter obj_adapter;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    ArrayList<String> imagepathuri;
    public static int REQUEST_PERMISSIONS = 1;
    public boolean boolean_permission;
    File dir;
    PrograssDilogbox prograssDilogbox;
    int Result_code = 1;
    String pathimg;
    Button login,Registation;
CircleImageView circleImageView;
LinearLayout hader;
private popuobox p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         p = new popuobox();
         fab = findViewById(R.id.fab);
         fab1 = findViewById(R.id.fab1);

         fab5 = findViewById(R.id.fab5);
         fab6 = findViewById(R.id.fab11);

        lv_pdf = findViewById(R.id.lv_pdf);
        prograssDilogbox=new PrograssDilogbox(homeActivity.this,R.style.CustomDialog);
        init();
        visveltygon();
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!visivel){

                    visivel=true;
                    converter(view);
                }
                else{
                    visivel=false;
                    visveltygon();
                }

            }
        });
         drawer = findViewById(R.id.drawer_layout);



        mDrawerToggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_app_bar_open_drawer_description,R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
       // navigationView.getCheckedItem();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        login=toolbar.findViewById(R.id.login_btn);
//    login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setLogin(v);
//            }
//        });



    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    private void converter(View view) {
        visveltycome();
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visveltygon();
                startActivity(new Intent(homeActivity.this,Textscanner.class));

            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visveltygon();
                Intent intent = new Intent(homeActivity.this,CustomCameraUI.class);
                startActivity(intent);
            }
        });


    }

    private void visveltycome() {
        fab1.setVisibility(View.VISIBLE);

        fab5.setVisibility(View.VISIBLE);
        fab6.setVisibility(View.VISIBLE);
    }

    private void visveltygon() {
        fab1.setVisibility(View.GONE);
        fab6.setVisibility(View.GONE);

        fab5.setVisibility(View.GONE);
    }


    private void init() {

        dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        fn_permission();


        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 pathimg = lv_pdf.getAdapter().getItem(i).toString();
                Log.e("Position", lv_pdf.getAdapter().getItem(i).toString() + "");

                viewPdf(pathimg);

            }
        });
        lv_pdf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 pathimg = lv_pdf.getAdapter().getItem(position).toString();

                showToast.show(pathimg,homeActivity.this);
                return false;
            }
        });
    }

    private void fn_permission() {
        prograssDilogbox.show();

        if ((ContextCompat.checkSelfPermission(homeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(homeActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {


            } else {
                ActivityCompat.requestPermissions(homeActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;

            getfile(dir);

            obj_adapter = new PDFAdapter(homeActivity.this, fileList);
            lv_pdf.setAdapter(obj_adapter);
            prograssDilogbox.dismiss();


        }
    }
    public ArrayList<File> getfile(File dir) {
        prograssDilogbox.show();
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".pdf")) {

                        for (int j = 0; j < fileList.size(); j++) {
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {

                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return fileList;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;
                getfile(dir);
                prograssDilogbox.dismiss();
                obj_adapter = new PDFAdapter(getApplicationContext(), fileList);
                lv_pdf.setAdapter(obj_adapter);

            } else {
                Toast.makeText(homeActivity.this, "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }









    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);// FOR CHOOSING MULTIPLE IMAGES
        try {
            String realImagePath;
            // When an Image is picked
            imagepathuri=new ArrayList<>();
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        realImagePath = getPath(homeActivity.this, imageUri);
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        Log.e("ImagePath", "onActivityResult " + realImagePath);
                        imagepathuri.add(realImagePath);

                    }
                    imagetopdf(imagepathuri);

                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    realImagePath = getPath(this, imageUri);
                    imagepathuri.add(realImagePath);
                    imagetopdf(imagepathuri);

                    Log.e("ImagePath", "onActivityResult: " + realImagePath);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public String getPath(final homeActivity context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(homeActivity.this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getDataColumn(Activity context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void imagetopdf(ArrayList<String> mArrayUri) {
        for(int i=0;i<=mArrayUri.size(); i++){

            Log.v("LOG_TAG", "Selected Images  " + mArrayUri.get(i));

        }

        p.popuobox(homeActivity.this, mArrayUri, getResources());


    }


    private void viewPdf(String n) {
        Intent intent;
        File file = new File( n);
        intent = new Intent(Intent.ACTION_VIEW);

        Uri contentUri;
        contentUri = Uri.fromFile(file);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= 24) {

            Uri apkURI = FileProvider.getUriForFile(homeActivity.this, this.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(apkURI, "application/pdf");
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, n));

        } else {

            intent.setDataAndType(contentUri, "application/pdf");
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Select Application for Open file"));
        }


}
public void setLogin(View view){
        startActivity(new Intent(homeActivity.this,loginScreen.class));
        showToast.show("click",this);
}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}