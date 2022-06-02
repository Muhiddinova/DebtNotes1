package uz.bmb.debtnotes.ui.debt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.repository.Repository

class DebtsFragmentViewModel(dataSource: Dao) : ViewModel() {
    private val repo = Repository(dataSource)
    private var user: LiveData<List<Model>>? = null

    init {

        user = repo.getDebt()
    }

    fun getUser(): LiveData<List<Model>>? {
        return user
    }
}