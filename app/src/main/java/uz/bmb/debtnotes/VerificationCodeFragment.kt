package uz.bmb.debtnotes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class VerificationCodeFragment : Fragment() {

    companion object {
        fun newInstance() = VerificationCodeFragment()
    }

    private lateinit var viewModel: VerificationCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.verification_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            VerificationCodeViewModel::class.java
        )
        // TODO: Use the ViewModel
    }

}