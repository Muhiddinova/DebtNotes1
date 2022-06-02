package uz.bmb.debtnotes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import uz.bmb.debtnotes.database.Dao
import uz.bmb.debtnotes.model.Model

class Repository(private val dataSource: Dao) {
    private val TAG = "Repository"
    suspend fun insertUser(user: Model): Long {
        Log.d(TAG, "insertUser: $user")
        return dataSource.insertUser(user)
    }


    fun getDebt():LiveData<List<Model>>{
        return dataSource.getDebts()
    }
    fun getUser(): LiveData<List<Model>> {
        return dataSource.getAlphabetizeTeacher()
    }
    fun priceDifference():LiveData<List<Model>>{
        return dataSource.priceDifference()
    }

}