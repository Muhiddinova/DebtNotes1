package uz.bmb.debtnotes.ui.add

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.database.RoomDatabase
import uz.bmb.debtnotes.databinding.FragmentAddDebtBinding
import uz.bmb.debtnotes.listeners.EditTextListeners


class AddDebtFragment : BottomSheetDialogFragment(), EditTextListeners {

    private val TAG = "AddDebtFragment"
    private lateinit var binding: FragmentAddDebtBinding
    private lateinit var viewModel: AddDebtsFragmentViewModel
    lateinit var radioButton: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_debt, container, false)
        val dataSource = RoomDatabase.getDatabase(requireContext())
//      var radioGroup: RadioGroup? = null
        val factory = AddDebtFVMFactory(this, dataSource.userDao)
        viewModel = ViewModelProvider(this, factory)[AddDebtsFragmentViewModel::class.java]
        binding.addViewModel = viewModel
        viewModel.userName = arguments?.getString("FIO")
        viewModel.phoneNumber = arguments?.getString("phone")
        viewModel.addresss = arguments?.getString("address")
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioBtn1 -> {
                        Log.d(TAG, "onCreateView: bosildi")
                        binding.etDebt.setText("+")
                        viewModel.debtAmount="-"
                    }
                    R.id.radioBtn2 -> {
                        binding.etDebt.setText("-")
                        viewModel.debtAmount="-"
                    }

                }
            }

        binding.btn.setOnClickListener {
            val intSelectButton: Int = binding.radioGroup.checkedRadioButtonId

            radioButton = binding.radioGroup.findViewById(intSelectButton)
            Toast.makeText(requireContext(), radioButton.text, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    override fun onError(type: String) {

        Log.d(TAG, "onError: hfwhi")
        when (type) {
            "FIO" -> {
                binding.etUsername.error = "FIO is required"
            }
            "date" -> {
                binding.etDate.error = "Date is required"
            }
            "debt" -> {
                binding.etDebt.error = "Debt is required"
            }

        }
    }

  private  fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioBtn1 ->
                    if (checked) {
                        Log.d(TAG, "onRadioButtonClicked: wwc")
                    }
                R.id.radioBtn2 ->
                    if (checked) {
                        binding.etDebt.setText("-")
                        viewModel.debtAmount = "-"
                    }
            }
        }
    }

}