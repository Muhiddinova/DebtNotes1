package uz.bmb.debtnotes.ui.add

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.bmb.debtnotes.listeners.EditTextListeners
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class AddDebtsFragmentViewModel(private val listener: EditTextListeners, dataSource: Dao) :
    ViewModel() {
    private val TAG = "DebtsFragmentViewModel"
    var userName: String? = null
    var addresss: String? = null
    var phoneNumber: String? = null
    var email: String = ""
    var comment: String? = null
    var date:String?=SimpleDateFormat("HH:mm  dd/MM/yyyy ", Locale.getDefault()).format(Date())
    var debtAmount: String?=null
    private val repo = Repository(dataSource)
    fun addButtonClicked(view: View) {
        if (!userName.isNullOrEmpty()) {
            if (!addresss.isNullOrEmpty()) {
                if (!phoneNumber.isNullOrEmpty()) {
                    if (!comment.isNullOrEmpty()) {
                        val user = Model(
                            userName!!,
                            addresss!!,
                            phoneNumber!!,
                            email,
                            comment!!,
                            date!!,
                            debtAmount!!
                        )
                        Log.d(TAG, "addButtonClicked: $user")
                        viewModelScope.launch {
                            val rowId = withContext(IO) {
                                repo.insertUser(user)
                            }
                            Log.d(TAG, "addButtonClicked: $rowId")
                        }
                    } else {
                        listener.onError("subject")
                    }
                } else {
                    listener.onError("subject")
                }

            } else {
                listener.onError("subject")
            }


        } else {
            listener.onError("subject")
        }
    }


}