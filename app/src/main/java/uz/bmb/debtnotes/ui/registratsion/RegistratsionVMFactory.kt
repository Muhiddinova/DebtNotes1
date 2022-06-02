package uz.bmb.debtnotes.ui.registratsion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.bmb.debtnotes.listeners.EditTextListeners
import uz.bmb.debtnotes.database.ProfileDao

class RegistrationVMFactory(private val listener: EditTextListeners, private val dataSource:ProfileDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(listener,dataSource) as T
        } else {
            throw IllegalArgumentException("AddViewModel class not found")
        }
    }
}