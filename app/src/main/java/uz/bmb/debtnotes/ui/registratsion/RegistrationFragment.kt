package uz.bmb.debtnotes.ui.registratsion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.bmb.debtnotes.listeners.EditTextListeners
import com.google.android.material.appbar.AppBarLayout
import uz.bmb.debtnotes.App
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.databinding.RegistrationFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

val PREFERENCES = "PREFERENCES"

class RegistrationFragment : Fragment(), EditTextListeners {
    private val TAG = "RegistrationFragment"
    var isAllFieldsChecked = false

    private lateinit var binding: RegistrationFragmentBinding

    private lateinit var viewModel: RegistrationViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.registration_fragment, container, false)
        val currentTime:String = SimpleDateFormat("HH:mm  dd/MM/yyyy ", Locale.getDefault()).format(Date())
        val maxLength = 75
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("true", true)
        editor.commit()

        val dataSource=uz.bmb.debtnotes.database.RoomDatabase.getDatabase(requireContext())
        val factory=RegistrationVMFactory(this,dataSource.profile)
        viewModel=ViewModelProvider(this,factory)[RegistrationViewModel::class.java]
        binding.profile=viewModel

        binding.editUserName.filters = arrayOf<InputFilter>(LengthFilter(maxLength))

        val maxLength2 = 75
        binding.editAddress.filters = arrayOf<InputFilter>(LengthFilter(maxLength2))

        val maxLength3 = 75
        binding.editPhoneNumber.filters = arrayOf<InputFilter>(LengthFilter(maxLength3))

        val maxLength4 = 75
        binding.editEmail.filters = arrayOf<InputFilter>(LengthFilter(maxLength4))

        val maxLength5 = 75
        binding.btnContinue.setOnClickListener {
            viewModel.saveProfileData(binding.btnContinue)
            var result: String? = null
            result =
                "FIO: " + binding.editUserName.text.toString() + "\nAddres: " + binding.editAddress.text
                    .toString() + "\nPhoneNumber:" + binding.editPhoneNumber.text.toString() + "\nEmail:" + binding.editEmail.text
                    .toString()
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                findNavController().navigate(
                    R.id.qrGeneratorDisplayFragment,
                    bundleOf("gn" to result, "type" to "BizCard_TYPE")
                )
                Log.d(TAG, "onCreateView: $result")
            }
        }
        App.name = binding.editUserName.toString()
        Log.d(TAG, "onCreateView: ${App.name}")

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        activity?.findViewById<AppBarLayout>(R.id.app_bar)?.visibility = View.GONE


    }

    override fun onPause() {
        super.onPause()
        activity?.findViewById<AppBarLayout>(R.id.app_bar)?.visibility = View.VISIBLE
    }

    private fun checkAllFields(): Boolean {
        if (binding.editUserName.length() == 0) {
            binding.editUserName.error = "This field is required"
            return false
        }
        if (binding.editAddress.length() == 0) {
            binding.editAddress.error = "This field is required"
            return false
        }
        if (binding.editPhoneNumber.length() == 0) {
            binding.editPhoneNumber.error = "Password is required"
            return false
        }


        // after all validation return true.
        return true
    }

    override fun onError(type: String) {
        Log.d(TAG, "onError: hfwhi")
        when (type) {
            "FIO" -> {
                binding.editUserName.error = "FIO is required"
            }


        }
    }


}