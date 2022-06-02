package uz.bmb.debtnotes.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class Profile(
    val profileName:String,
    val profilePhoneNumber:String,
    val profileAddress:String,
    val profileEmail:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
