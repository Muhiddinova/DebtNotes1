package uz.bmb.debtnotes.ui.registratsion

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Runnable
import uz.bmb.debtnotes.R
import uz.bmb.debtnotes.databinding.FragmentSplashBinding
import uz.bmb.debtnotes.prefs
import java.util.*

const val PREFS_NAME = "MyPrefsFile"

class SplashFragment : Fragment() {

    private lateinit var timer: Timer


    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)




        return binding.root
    }


    private fun waitAndOpenOtherFragment() {
        val mainHandler = context?.let { Handler(it.mainLooper) }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val runnable = Runnable {
                    findNavController().navigate(R.id.pinCodeWriteFragment)

                }
                mainHandler?.post(runnable)
            }
        }, 2000)
    }


    override fun onResume() {
        super.onResume()

        activity?.findViewById<AppBarLayout>(R.id.app_bar)?.visibility = View.GONE


        if (prefs.isFirstTimeLaunch()) {
            binding.arrow.visibility = View.GONE
            binding.splashQr.visibility = View.GONE
            binding.splashTx.visibility = View.GONE
            waitAndOpenOtherFragment()
        } else {
            binding.arrow.visibility = View.VISIBLE
            binding.splashQr.visibility = View.VISIBLE
            binding.splashTx.visibility = View.VISIBLE
            binding.arrow.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            binding.splashQr.setOnClickListener {
                findNavController().navigate(R.id.qrGeneratorDisplayFragment)
            }

        }

    }

    override fun onPause() {
        super.onPause()
        activity?.findViewById<AppBarLayout>(R.id.app_bar)?.visibility = View.VISIBLE
    }


}