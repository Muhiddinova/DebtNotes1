package uz.bmb.debtnotes.ui.registratsion

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.bmb.debtnotes.listeners.EditTextListeners
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.bmb.debtnotes.database.ProfileDao
import uz.bmb.debtnotes.model.Profile
import uz.bmb.debtnotes.repository.ProfileRepo


class RegistrationViewModel(private val listener: EditTextListeners, dataSource:ProfileDao) : ViewModel() {
    private val TAG="RegistrationFragmentVM"
     var profileName:String?=null
    var profilePhoneNumber:String?=null
    var profileAddress:String?=null
    var profileEmail:String?=null
    private val repo=ProfileRepo(dataSource)
    fun saveProfileData(view: View){
        if (!profileName.isNullOrEmpty()){
            if (!profilePhoneNumber.isNullOrEmpty()){
                if (!profileAddress.isNullOrEmpty()){
                    if (!profileEmail.isNullOrEmpty()){
                        val profile=Profile(
                            profileName!!,
                            profilePhoneNumber!!,
                            profileAddress!!,
                            profileEmail!!
                        )
                        Log.d(TAG, "saveProfileData: ${profile.profileName}")
                        viewModelScope.launch {
                            val rowId= withContext(IO){
                                repo.insertProfile(profile)
                            }
                            Log.d(TAG, "saveProfileData: $rowId")
                        }
                    }
                }
                else{
                    listener.onError("profileAddress")
                }
            }
            else{
                listener.onError("profilePhoneNumber")
            }
        }
        else {
            listener.onError("profileName")
        }
    }

}