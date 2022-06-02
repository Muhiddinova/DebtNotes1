package uz.bmb.debtnotes.ui.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.database.RoomDatabase
import uz.bmb.debtnotes.databinding.FragmentAllDebtsBinding
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.ui.debt.DebtsFVMFactory
import uz.bmb.debtnotes.ui.debt.DebtsFragmentViewModel
import uz.bmb.debtnotes.ui.adapter.UserAdapter

class AllDebtsFragment : Fragment(), UserAdapter.UserItemListener {
private lateinit var binding:FragmentAllDebtsBinding
private lateinit var viewModel:AllDebtsFragmentVM
private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_all_debts, container, false)
        val dataSource=RoomDatabase.getDatabase(requireContext())
        val factory=AllDebtsVMFactory(dataSource.userDao)
        viewModel=ViewModelProvider(this,factory)[AllDebtsFragmentVM::class.java]

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRv()
        viewModel.getUser()?.observe(this){
            adapter.updateData(it as ArrayList<Model>)
        }
    }
    private fun setRv(){
        adapter= UserAdapter(this)
        binding.rvDebtAll.adapter=adapter

    }

    override fun onClick(user: Model) {

    }

}