package uz.bmb.debtnotes.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.listeners.EditTextListeners

class AddDebtFVMFactory(private val listener: EditTextListeners, private val dataSource: uz.bmb.debtnotes.database.Dao):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDebtsFragmentViewModel::class.java)){
            return AddDebtsFragmentViewModel(listener,dataSource) as T
        } else {
            throw IllegalArgumentException("AddViewModel class not found")
        }
    }
}