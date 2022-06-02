package uz.bmb.debtnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import uz.bmb.debtnotes.model.Model
import uz.bmb.debtnotes.model.Profile

@Database(
    entities = [
        Model::class,
               Profile::class], version = 2, exportSchema = false
)
abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract val userDao: Dao
    abstract val profile:ProfileDao

    companion object {
        private var INSTANCE: RoomDatabase? = null
        fun getDatabase(context: Context): RoomDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                val database = Room.databaseBuilder(context, RoomDatabase::class.java, "user")
                    .build()
                INSTANCE = database
                return INSTANCE!!
            }
        }
    }

}