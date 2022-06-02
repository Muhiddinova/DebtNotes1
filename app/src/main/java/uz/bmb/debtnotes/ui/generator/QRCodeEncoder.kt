package uz.bmb.debtnotes.ui.generator

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.util.*


class QRCodeEncoder(data: String?, bundle: Bundle?, type: String, format: String?, dimension: Int) {
    private var dimension = Int.MIN_VALUE
    var contents: String? = null
        private set
    var displayContents: String? = null
        private set
    var title: String? = null
        private set
    private var format: BarcodeFormat? = null
    private var encoded = false
    private fun encodeContents(
        data: String?,
        bundle: Bundle?,
        type: String,
        formatString: String?
    ): Boolean {
        // Default to QR_CODE if no format given.
        format = null
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString)
            } catch (iae: IllegalArgumentException) {
                // Ignore it then
            }
        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            format = BarcodeFormat.QR_CODE
            encodeQRCodeContents(data, bundle, type)
        } else if (data != null && data.length > 0) {
            contents = data
            displayContents = data
            title = "Text"
        }
        return contents != null && contents!!.length > 0
    }

    private fun encodeQRCodeContents(data: String?, bundle: Bundle?, type: String) {
        var data = data
        if (type == "BizCard_TYPE") {
            data = trim(data)
            if (data != null) {
                contents = "$data"
                displayContents = data
                title = "BizCard"
            }
        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(): Bitmap? {
        if (!encoded) return null
        var hints: MutableMap<EncodeHintType?, Any?>? = null
        val encoding = guessAppropriateEncoding(contents)
        if (encoding != null) {
            hints = EnumMap(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = encoding
        }
        val writer = MultiFormatWriter()
        val result = writer.encode(contents, format, dimension, dimension, hints)
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        // All are 0, or black, by default
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result[x, y]) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    companion object {
        private const val WHITE = -0x1
        private const val BLACK = -0x1000000
        private fun guessAppropriateEncoding(contents: CharSequence?): String? {
            // Very crude at the moment
            for (i in 0 until contents!!.length) {
                if (contents[i].toInt() > 0xFF) {
                    return "UTF-8"
                }
            }
            return null
        }

        private fun trim(s: String?): String? {
            if (s == null) {
                return null
            }
            val result = s.trim { it <= ' ' }
            return if (result.length == 0) null else result
        }

        private fun escapeMECARD(input: String?): String? {
            if (input == null || input.indexOf(':') < 0 && input.indexOf(';') < 0) {
                return input
            }
            val length = input.length
            val result = StringBuilder(length)
            for (i in 0 until length) {
                val c = input[i]
                if (c == ':' || c == ';') {
                    result.append('\\')
                }
                result.append(c)
            }
            return result.toString()
        }
    }

    init {
        this.dimension = dimension
        encoded = encodeContents(data, bundle, type, format)
    }
}