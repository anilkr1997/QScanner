package com.bspl.qscanner;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bspl.qscanner.core.Camera2
import com.bspl.qscanner.core.comprafhing
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_custom_camera_ui.*
import mobin.customcamera.core.Converters

import java.io.File

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

class CustomCameraUI : AppCompatActivity() {

    private lateinit var camera2: Camera2
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_camera_ui)
        init()

    }

    private fun init() {

        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        )

            initCamera2Api()
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 3)
            else initCamera2Api()

        }

    }

    private fun initCamera2Api() {
        camera2 = Camera2(this, camera_view)
        var gpath: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath;
        var spath = "QScanner"
        var fullpath = File(gpath + File.separator + spath)
        Log.w("fullpath", "" + fullpath)
        imageReaderNew(fullpath)
        iv_rotate_camera.setOnClickListener {
            camera2.switchCamera()
        }
        iv_capture_image.setOnClickListener { v ->
            camera2.takePhoto {
                Toast.makeText(v.context, "Saving Picture", Toast.LENGTH_SHORT).show()
                disposable = Converters.convertBitmapToFile(it) { file ->
                    iv_gallery.setImageURI(Uri.parse(file.path))
                   // comprafhing.compressImage(this,fullpath.toString())

                   // Toast.makeText(v.context, "Saved Picture Path ${file.path}", Toast.LENGTH_SHORT).show()
                }
            }


        }

        iv_camera_flash_on.setOnClickListener {
            camera2.setFlash(Camera2.FLASH.ON)
            it.alpha = 1f
            iv_camera_flash_auto.alpha = 0.4f
            iv_camera_flash_off.alpha = 0.4f
        }


        iv_camera_flash_auto.setOnClickListener {
            iv_camera_flash_off.alpha = 0.4f
            iv_camera_flash_on.alpha = 0.4f
            it.alpha = 1f
            camera2.setFlash(Camera2.FLASH.AUTO)
        }

        iv_camera_flash_off.setOnClickListener {
            camera2.setFlash(Camera2.FLASH.OFF)
            it.alpha = 1f
            iv_camera_flash_on.alpha = 0.4f
            iv_camera_flash_auto.alpha = 0.4f

        }
        iv_gallery.setOnClickListener {
            val intent = Intent(this, AllImagefrommobile::class.java)
            intent.putExtra("flage"," ")
            startActivity(intent)
        }
    }

    private fun imageReaderNew(fullpath: File) {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = fullpath.listFiles()
        var imagepathone = "";
        if (listAllFiles != null && listAllFiles.size > 0) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpeg")) {
                    // File absolute path
                    Log.e("downloadFilePath", currentFile.getAbsolutePath())
                    // File Name
                    Log.e("downloadFileName", currentFile.getName())
                    fileList.add(currentFile.absoluteFile)
                }
            }
            for (i in 0..fileList.size - 1) {
                imagepathone = fileList.get(i).toString()
            }
            Log.e("downloadFileName--", imagepathone)
            // val assetsBitmap: Bitmap? = get("flower8.jpg")
            iv_gallery.setImageURI(Uri.parse(imagepathone));

        } else {
            iv_gallery.setImageDrawable(R.drawable.ic_image_icon_01)
        }

    }


    override fun onPause() {
        //  cameraPreview.pauseCamera()
        camera2.close()
        super.onPause()
    }

    override fun onResume() {
        // cameraPreview.resumeCamera()
        camera2.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroy()
    }


}

private fun CircleImageView.setImageDrawable(icImageIcon01: Int) {

}


