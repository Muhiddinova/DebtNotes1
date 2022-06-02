package uz.bmb.debtnotes.ui.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.repository.Repository

class AllDebtsFragmentVM(dataSource:Dao):ViewModel() {
    private val repo = Repository(dataSource)
    private var user: LiveData<List<Model>>? = null

    init {

        user = repo.getUser()
    }

    fun getUser(): LiveData<List<Model>>? {
        return user
    }
}