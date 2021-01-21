@file:Suppress("DEPRECATION")

package mobin.customcamera.core

import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {
    @JvmStatic
    fun convertBitmapToFile(bitmap: Bitmap, onBitmapConverted: (File) -> Unit): Disposable {
        return Single.fromCallable {
            compressBitmap(bitmap)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    Log.i("convertedPicturePath", it.path)
                    onBitmapConverted(it)
                }
            }, { it.printStackTrace() })
    }

    private fun compressBitmap(bitmap: Bitmap): File? {
        //create a file to write bitmap data

        try {
            val myStuff =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BsplQScanner")
            if (!myStuff.exists())
                myStuff.mkdirs()
            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                LocalDateTime.now()

            } else {
                TODO("VERSION.SDK_INT < O")
            }


            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            val formatted = current.format(formatter)
            Log.i("formatted", formatted)

            val picture = File(myStuff.absolutePath, "Mobin-" +formatted+ ".jpeg")

            //Convert bitmap to byte array

            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
            val bitmapData = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(picture)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            return picture
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }


}
