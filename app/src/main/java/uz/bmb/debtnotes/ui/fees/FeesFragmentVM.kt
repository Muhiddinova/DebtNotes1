package uz.bmb.debtnotes.ui.fees

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.repository.Repository

class FeesFragmentVM(dataSource:Dao):ViewModel() {
    private val repo=Repository(dataSource)
    private var user:LiveData<List< Model>>?=null
    init {
        user=repo.priceDifference()
    }
    fun priceDifference():LiveData<List<Model>>?{
        return user
    }
}