package uz.bmb.debtnotes.repository

import android.util.Log
import uz.bmb.debtnotes.database.ProfileDao
import uz.bmb.debtnotes.model.Profile

class ProfileRepo(private val dataSource:ProfileDao) {
    suspend fun insertProfile(profile: Profile):Long{
        return dataSource.insertProfile(profile)
        Log.d("ProfileRepository", "insertProfile: ${profile.profileName}")
    }
}