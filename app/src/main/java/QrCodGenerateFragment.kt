//package uz.bmb.debtnotes
//
//import android.R
//import android.os.Bundle
//import android.text.Editable
//import android.text.InputType
//import android.text.TextWatcher
//import android.view.View
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//
//
//class QrCodGenerateFragment : AppCompatActivity() {
//    var enter_mpin: EditText? = null
//    var i1: ImageView? = null
//    var i2: ImageView? = null
//    var i3: ImageView? = null
//    var i4: ImageView? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_qr_cod_generate)
//        i1 = findViewById<View>(R.id.imageview_circle1) as ImageView
//        i2 = findViewById<View>(R.id.imageview_circle2) as ImageView
//        i3 = findViewById<View>(R.id.imageview_circle3) as ImageView
//        i4 = findViewById<View>(R.id.imageview_circle4) as ImageView
//        enter_mpin = findViewById<View>(R.id.editText_enter_mpin) as EditText
//        enter_mpin!!.requestFocus()
//        enter_mpin!!.inputType = InputType.TYPE_CLASS_NUMBER
//        enter_mpin!!.isFocusableInTouchMode = true
//        enter_mpin!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable) {
//                Log.d(TAG, "onKey: screen key pressed")
//                i1.setImageResource(R.drawable.circle2)
//            }
//        })
//    }
//
//    companion object {
//        private const val TAG = "LoginActivity"
//    }
//}