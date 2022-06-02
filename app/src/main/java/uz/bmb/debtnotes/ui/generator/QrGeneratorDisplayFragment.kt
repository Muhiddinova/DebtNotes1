package uz.bmb.debtnotes.ui.generator
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.databinding.FragmentQrGeneratorDisplayBinding
import uz.bmb.debtnotes.prefs
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class QrGeneratorDisplayFragment : Fragment() {
    private lateinit var binding: FragmentQrGeneratorDisplayBinding
    private var TAG = "QrGeneratorDisplayFragment"
    var clipboardManager: ClipboardManager? = null
    var clipData: ClipData? = null

    var qrInputText = ""
    var qrInputType: String = "UNDEFINED"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_qr_generator_display,
            container,
            false
        )
        if (prefs.isFirstTimeLaunch()) {

            prefs.setFirstTimeLaunch(false)
        }




        qrInputText = arguments?.getString("gn").toString()
        Log.d(TAG, "onCreateView: $qrInputText")


        qrInputType = arguments?.getString("type")!!

        val image = Glide.with(binding.imageView.context)
            .load(QRGeneratorUtils().createImage(requireActivity(), qrInputText, qrInputType))
            .into(binding.imageView)
        Log.d(TAG, "onCreateView: ${image}")
        val bitmap: Bitmap? = encodeAsBitmap(qrInputText, 300, 300)
        binding.imageView.setImageBitmap(bitmap)
//        createImageFile(bitmap)
        binding.btnsave.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        return binding.root


    }

    private fun encodeAsBitmap(str: String, WIDTH: Int, HEIGHT: Int): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null)

        } catch (iae: IllegalArgumentException) {
            return null
        }
        val width = result.width
        val height = result.height
        val pixel = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixel[offset + x] = if (result.get(x, y)) -0x1000000 else -0x1
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixel, 0, width, 0, 0, width, height)
//        createImageFile(bitmap)
        return bitmap


    }

    private fun createImageFile(bitmapScaled: Bitmap?) {
        val bytes = ByteArrayOutputStream()
        bitmapScaled?.compress(Bitmap.CompressFormat.PNG, 40, bytes)

        val filepath =
            Environment.getDownloadCacheDirectory().absolutePath + File.separator + "test.png"
        val f = File(filepath)
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}