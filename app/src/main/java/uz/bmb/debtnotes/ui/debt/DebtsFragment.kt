package uz.bmb.debtnotes.ui.debt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.database.RoomDatabase
import uz.bmb.debtnotes.databinding.FragmentDebtsBinding
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.ui.adapter.UserAdapter


class DebtsFragment : Fragment(), UserAdapter.UserItemListener {
    private lateinit var binding: FragmentDebtsBinding
    private lateinit var viewModel: DebtsFragmentViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_debts, container, false)
        val dataSource = RoomDatabase.getDatabase(requireContext())
        val factory = DebtsFVMFactory(dataSource.userDao)
        viewModel = ViewModelProvider(this, factory)[DebtsFragmentViewModel::class.java]

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRv()
        viewModel.getUser()?.observe(this) {
            adapter.updateData(it as ArrayList<Model>)
        }
    }

    private fun setRv() {
        adapter = UserAdapter(this)
        binding.rvDebt.adapter = adapter

    }

    override fun onClick(user: Model) {
        TODO("Not yet implemented")
    }
}