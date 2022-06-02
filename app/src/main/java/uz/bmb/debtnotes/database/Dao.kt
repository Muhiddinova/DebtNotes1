package uz.bmb.debtnotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.bmb.debtnotes.model.Model

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Model): Long


    @Query("Select * from user ORDER BY userName ASC")
    fun getAlphabetizeTeacher(): LiveData<List<Model>>
    @Query("Select * from user where debtAmount like'-%'")
    fun priceDifference(): LiveData<List<Model>>
    @Query("Select * from user where debtAmount>0 ")
    fun getDebts(): LiveData<List<Model>>
}