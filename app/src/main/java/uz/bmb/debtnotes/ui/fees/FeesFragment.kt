package uz.bmb.debtnotes.ui.fees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.database.RoomDatabase
import uz.bmb.debtnotes.databinding.FragmentFeesBinding
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.ui.adapter.UserAdapter

class FeesFragment : Fragment(), UserAdapter.UserItemListener {
    private lateinit var binding: FragmentFeesBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FeesFragmentVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fees, container, false)
        val dataSource = RoomDatabase.getDatabase(requireContext())
        val factory = FeesFragmentVMFactory(dataSource.userDao)
        viewModel = ViewModelProvider(this, factory)[FeesFragmentVM::class.java]
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRv()
        viewModel.priceDifference()?.observe(this) {
            adapter.updateData(it as ArrayList<Model>)
        }
    }

    private fun setRv() {
        adapter = UserAdapter(this)
        binding.rvFees.adapter = adapter
    }

    override fun onClick(user: Model) {
        TODO("Not yet implemented")
    }
}