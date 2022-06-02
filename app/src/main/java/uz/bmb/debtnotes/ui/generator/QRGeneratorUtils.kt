package uz.bmb.debtnotes.ui.generator

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

class QRGeneratorUtils {

    private val IMAGE_FOLDER = "Generated QR Codes"
    private val TAG = QRGeneratorUtils::class.java.simpleName


    private var cache: Uri? = null

    fun createImage(context: Context, qrInputText: String?, qrType: String?): Uri? {

        //Find screen size
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        //Encode with a QR Code image
        val qrCodeEncoder = qrType?.let {
            QRCodeEncoder(
                qrInputText,
                null,
                it,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension
            )
        }
        var bitmap_: Bitmap? = null
        try {
            if (qrCodeEncoder != null) {
                bitmap_ = qrCodeEncoder.encodeAsBitmap()
            }
            // return bitmap_;
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return cacheImage(context, bitmap_)
    }
     fun cacheImage(context: Context, image: Bitmap?): Uri? {
        var imageFilePath = File(context.cacheDir, "images/")
        imageFilePath.mkdir()
        imageFilePath = File(imageFilePath, buildFileString())
        val file: File =writeToFile(imageFilePath, image)
        cache =
            FileProvider.getUriForFile(context, "com.example.qrcode", file)
        return cache
    }
    fun saveCachedImageToExternalStorage(context: Context) {
        val bitmap: Bitmap?
        try {
            bitmap =
                MediaStore.Images.Media.getBitmap(context.contentResolver, cache)
           saveImageToExternalStorage(context, bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun saveImageToExternalStorage(context: Context, finalBitmap: Bitmap) {
        val fileName: String = buildFileString()!!
        val oStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val imageCollection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val relativePath =
                Environment.DIRECTORY_PICTURES + File.separator + IMAGE_FOLDER
            val newImage = ContentValues()
            newImage.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            newImage.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            newImage.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, relativePath)
            val imageUri = resolver.insert(imageCollection, newImage)
            oStream = resolver.openOutputStream(imageUri!!)!!
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream)
        } else {
            val externalPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val myDir = File(externalPath, IMAGE_FOLDER)
            myDir.mkdirs()
            val file: File = writeToFile(File(myDir, fileName), finalBitmap)

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(
                context, arrayOf(file.toString()), null
            ) { path: String, uri: Uri ->
                Log.i("ExternalStorage", "Scanned $path:")
                Log.i("ExternalStorage", "-> uri=$uri")
            }
        }
    }

    fun buildFileString(): String? {
        // Define name
        val sb = StringBuffer()
        sb.append("QrCode_")
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.format(Calendar.getInstance().time, sb, FieldPosition(SimpleDateFormat.DATE_FIELD))
        sb.append(".png")
        return sb.toString()
    }

     fun writeToFile(file: File, image: Bitmap?): File {
        var outFile = file
        val sb = StringBuilder(file.toString())

        // if multiple codes are generated on the same day.. name them with numbers
        var i = 2
        while (outFile.exists()) {
            sb.delete(sb.length - 4, sb.length)
            sb.append("_(").append(i).append(").png")
            outFile = File(sb.toString())
            i++
        }
        try {
            FileOutputStream(outFile).use { fOut ->
                image?.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    fOut
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null!!
        }
        return outFile
    }
}