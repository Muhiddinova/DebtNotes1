package uz.bmb.debtnotes.ui.registratsion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.databinding.FragmentPinCodeWriteBinding


class PinCodeWriteFragment : Fragment() {
    private lateinit var binding: FragmentPinCodeWriteBinding
    private var counter = ""
    private val editTextArray: ArrayList<EditText> = ArrayList(NUM_OF_DIGITS)
    private val image: ArrayList<ImageView> = ArrayList(NUM_OF_DIGITS)
    private lateinit var numTemp: String

    companion object {

        const val NUM_OF_DIGITS = 4
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pin_code_write, container, false)


//        editTextArray[0].requestFocus()

        binding.btn0.setOnClickListener {
          onChangedNumber("0")
            binding.imageviewCircle1.setImageResource(R.drawable.ic_ellipse_2)
            Log.d("pin", "onCreateView: $counter")

//            for (index in 0 until (binding.editPinCode.childCount)) {
//                val view: View = binding.editPinCode.getChildAt(index)
//                if (view is ImageView) {
//                    image.add(index,R.drawable.ic_circle)
//                }
//            }
        }
        binding.btn1.setOnClickListener {
            onChangedNumber("1")

        }
        binding.btn2.setOnClickListener {
            onChangedNumber("2")
        }
        binding.btn3.setOnClickListener {
            onChangedNumber("3")
        }
        binding.btn4.setOnClickListener {
            onChangedNumber("4")
        }
        binding.btn5.setOnClickListener {
            onChangedNumber("5")
        }
        binding.btn6.setOnClickListener {

            onChangedNumber("6")
        }
        binding.btn7.setOnClickListener {
            onChangedNumber("7")
        }
        binding.btn8.setOnClickListener {
            onChangedNumber("8")
        }
        binding.btn9.setOnClickListener {
            onChangedNumber("9")
        }

        return binding.root
    }

    private fun onChangedNumber(digit: String){
        when(counter.length){
            0->{
                counter+=digit
                binding.imageviewCircle1.setImageResource(R.drawable.ic_ellipse_2)
            }
            1->{
                counter+=digit
                binding.imageviewCircle2.setImageResource(R.drawable.ic_ellipse_2)
            }
            2->{
                counter+=digit
                binding.imageviewCircle3.setImageResource(R.drawable.ic_ellipse_2)
            }
            3->{
                counter+=digit
                binding.imageviewCircle4.setImageResource(R.drawable.ic_ellipse_2)
            }
        }
    }


}