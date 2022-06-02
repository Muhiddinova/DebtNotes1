package uz.bmb.debtnotes.ui.debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.database.Dao

class DebtsFVMFactory(private val dataSource:Dao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DebtsFragmentViewModel::class.java)){
            return DebtsFragmentViewModel(dataSource) as T
        } else{
            throw IllegalArgumentException("TeacherViewModel not found")
        }
    }

}