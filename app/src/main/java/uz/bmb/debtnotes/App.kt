package uz.bmb.debtnotes

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import uz.bmb.debtnotes.ui.registratsion.PREFS_NAME
import kotlin.properties.Delegates

val prefs: Prefs by lazy {
    App.prefs!!
}
class App():Application() {

    companion object {
        var prefs: Prefs? = null
        lateinit var name: String
        var counter by Delegates.notNull<Int>()
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)
    }


}

class Prefs(context: Context){
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

          fun setFirstTimeLaunch(isFirstTime: Boolean) {
        sharedPreferences.edit().putBoolean("APP_PREF_INT_EXAMPLE", isFirstTime).apply()
    }

    fun isFirstTimeLaunch(): Boolean {
      return  sharedPreferences.getBoolean("APP_PREF_INT_EXAMPLE", true)
    }

}
