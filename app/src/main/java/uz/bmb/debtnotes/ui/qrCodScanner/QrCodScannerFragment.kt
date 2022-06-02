package uz.bmb.debtnotes.ui.qrCodScanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.integration.android.IntentIntegrator
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.databinding.FragmentQrCodScannerBinding


class QrCodScannerFragment :  BottomSheetDialogFragment(){
    private var TAG = "QrCodScannerFragment"
    private  var resultName=""
    private lateinit var binding: FragmentQrCodScannerBinding
    private lateinit var mQrResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_qr_cod_scanner, container, false)

        mQrResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

                    Log.d(TAG, "onCreateViewwww: ${resultName}")
                    if (result.contents != null) {
                        // Do something with the contents (this is usually a URL)
                        println(result.contents)
//                        val user=Model(
//                            resultName.split("\\n".toRegex())[0].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[1].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[2].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[3].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[4].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[5].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[6].split("\\s".toRegex())[2],
//                        )
                        resultName=result.contents.toString()
                        binding.textView.text=resultName
                        val spec=resultName.split("\\n".toRegex())[3]
//                        Log.d(TAG, "Result: ${spec.split("\\s ".toRegex())[1]}")
                        Log.d(TAG, "Result: ${spec.substring(spec.indexOf(' '), spec.length)}")
                        Log.d(TAG, "onCreateView: $result")
                    }
                }
            }
        binding.qrButton.setOnClickListener {
            startScanner()
        }



        return binding.root
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        var result = IntentIntegrator.parseActivityResult(resultCode, data)
//        if (result != null) {
//            AlertDialog.Builder(requireActivity())
//                .setMessage("Would you like to go to ${result.contents}?")
//                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
//                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
//                    intent.putExtra(SearchManager.QUERY,result.contents)
//                    startActivity(intent)
//                })
//                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->  })
//                .create()
//                .show()
//
//            Log.d(TAG, "onActivityResult: ${result}")
//        }
//    }
    private fun startScanner() {

        val scanner = IntentIntegrator(requireActivity())
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.setPrompt("QR kodni scanner qiling")
        mQrResultLauncher.launch(scanner.createScanIntent())

    }

}