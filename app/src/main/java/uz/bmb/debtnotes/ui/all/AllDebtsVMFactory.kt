package uz.bmb.debtnotes.ui.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.ui.add.AddDebtsFragmentViewModel

class AllDebtsVMFactory(private  var dataSource: Dao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllDebtsFragmentVM::class.java)){
            return AllDebtsFragmentVM(dataSource ) as T
        } else {
            throw IllegalArgumentException("AllVM class not found")
        }
    }
}