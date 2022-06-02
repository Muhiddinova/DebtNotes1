package uz.bmb.debtnotes.ui.fees

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.database.Dao

class FeesFragmentVMFactory(private val dataSource:Dao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeesFragmentVM::class.java)){
            return FeesFragmentVM(dataSource) as T
        } else {
            throw IllegalArgumentException("FeesFactory class not found")
        }
    }
}