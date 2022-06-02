package uz.bmb.debtnotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class Model(

    val userName: String = "",
    val address: String = "",
    val phoneNumber: String="",
    val eMail: String="",
    val comment: String="",
    val date: String,
    val debtAmount: String,
//    val debtSituation: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0


)