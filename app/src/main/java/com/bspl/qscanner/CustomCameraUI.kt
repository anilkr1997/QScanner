package com.bspl.qscanner;

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bspl.qscanner.core.Camera2
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_custom_camera_ui.*
import mobin.customcamera.core.Converters
import java.io.File

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

class CustomCameraUI : AppCompatActivity() {

    private lateinit var camera2: Camera2
    private var disposable: Disposable? = null
val PICK_IMAGE=1;
    var imagepathuri: ArrayList<String>? = null

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
        var gpath: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath;
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
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)

        }
    }

    private fun imageReaderNew(fullpath: File) {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = fullpath.listFiles()
        var imagepathone = "";
        if (listAllFiles != null && listAllFiles.size > 0) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpg")) {
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
            iv_gallery.setImageDrawable()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            var realImagePath: String
            // When an Image is picked
            imagepathuri =  ArrayList<String>()
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
                if (data.clipData != null) {
                    val count = data.clipData!!.itemCount //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        realImagePath = getPath(this, imageUri)!!
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        Log.e("ImagePath", "onActivityResult $realImagePath")
                        imagepathuri!!.add(realImagePath)
                    }
              topdf(imagepathuri!!)
                } else if (data.data != null) {
                    val imageUri = data.data
                    realImagePath = getPath(this, imageUri!!)!!
                    imagepathuri!!.add(realImagePath)
                    topdf(imagepathuri!!)
                    Log.e("ImagePath", "onActivityResult: $realImagePath")
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun topdf(imagepathuri: ArrayList<String>) {


    }

    fun getPath(context: CustomCameraUI, uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), id.toLong())
                return getDataColumn(this, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                        split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }


    fun getDataColumn(context: Activity, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
                column
        )
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs,
                    null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }


    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}

private fun CircleImageView.setImageDrawable() {

}


