package uz.bmb.debtnotes.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.zxing.integration.android.IntentIntegrator
import uz.bmb.debtnotes.*
import uz.bmb.debtnotes.databinding.HomeFragmentBinding
import uz.bmb.debtnotes.ui.all.AllDebtsFragment
import uz.bmb.debtnotes.ui.debt.DebtsFragment
import uz.bmb.debtnotes.ui.fees.FeesFragment
import uz.bmb.debtnotes.ui.registratsion.PREFERENCES


class   HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private var addButtonClicked = false
    private val rotateOpenAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_animation
        )
    }
    private  var resultName=""

    private lateinit var mQrResultLauncher: ActivityResultLauncher<Intent>
    private val rotateCloseAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_animation
        )
    }
    private val fromBottomAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_animation
        )
    }
    private val toBottomAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_animation
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        configureTopNavigation()
        binding.floatingActionButtonAdd.setOnClickListener {
            onAddButtonClicked()
        }
        binding.floatingActionButtonCall.setOnClickListener {
            findNavController().navigate(R.id.addDebtFragment)
        }
        binding.floatingActionButtonQr.setOnClickListener {
            startScanner()
        }
        Toast.makeText(requireContext(), "$resultName", Toast.LENGTH_SHORT).show()
        mQrResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
                    Log.d("HomeFragment", "onCreateViewwww: ${resultName}")
                    if (result.contents != null) {
                        println(result.contents)
//                        val user=Model(
//                            resultName.split("\\n".toRegex())[0].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[1].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[2].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[3].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[4].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[5].split("\\s".toRegex())[2],
//                            resultName.split("\\n".toRegex())[6].split("\\s".toRegex())[2],
//                        )
                        resultName=result.contents.toString()
                        val username=resultName.split("\\n".toRegex())[0]
                         val address=resultName.split("\\n".toRegex())[1]
                        val phone=resultName.split("\\n".toRegex())[2]
                        val eMail=resultName.split("\\n".toRegex())[3]
                        val time=resultName.split("\\n".toRegex())[5]

                        findNavController().navigate(R.id.addDebtFragment, bundleOf(
                            "FIO" to username.substring(username.indexOf(':')+1, username.length),
                            "address" to address.substring(address.indexOf(':')+1, address.length),
                            "phone" to phone.substring(phone.indexOf(':')+1, phone.length),
                            "email" to eMail.substring(eMail.indexOf(':')+1, eMail.length),



                        )


                        )

//                        Log.d(TAG, "Result: ${spec.split("\\s ".toRegex())[1]}")
                        Log.d("HomeFragment", "Result: ${username.substring(username.indexOf(' '), username.length)}")
                        Log.d("HomeFragment", "onCreateView: $result")
                    }
                }
            }


        val context: Context = requireActivity().applicationContext
        val settings: SharedPreferences = context.getSharedPreferences(PREFERENCES, 0)
        val editor = settings.edit()
        editor.putBoolean("isLogged", true)
        editor.apply()
        return binding.root
    }
    private fun startScanner() {

        val scanner = IntentIntegrator(requireActivity())
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.setPrompt("QR kodni scanner qiling")
        scanner.setOrientationLocked(false)
        mQrResultLauncher.launch(scanner.createScanIntent())


    }

    private fun configureTopNavigation() {
        binding.pager.adapter = TabLayoutAdapter(childFragmentManager, 3)

        binding.pager.offscreenPageLimit = 3
        binding.tabLayout.setupWithViewPager(binding.pager)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val adapter = TabLayoutAdapter(childFragmentManager, binding.tabLayout.tabCount)

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        binding.pager.adapter = adapter
        binding.pager.addOnAdapterChangeListener(object : TabLayout.OnTabSelectedListener,
            ViewPager.OnAdapterChangeListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.pager.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onAdapterChanged(
                viewPager: ViewPager,
                oldAdapter: PagerAdapter?,
                newAdapter: PagerAdapter?
            ) {

            }


        })

    }

    private fun onAddButtonClicked() {
        setVisibility(addButtonClicked)
        setAnimation(addButtonClicked)
        buttonSetClickable()

        if (!addButtonClicked) {
            addButtonClicked = true
        } else {
            addButtonClicked = false
        }
    }

    private fun setVisibility(buttonClicked: Boolean) {
        if (!buttonClicked) {
            binding.floatingActionButtonCall.visibility = VISIBLE
            binding.floatingActionButtonQr.visibility = VISIBLE
        } else {
            binding.floatingActionButtonCall.visibility = INVISIBLE
            binding.floatingActionButtonQr.visibility = INVISIBLE
        }
    }

    private fun setAnimation(buttonClicked: Boolean) {
        if (!buttonClicked) {
            binding.floatingActionButtonCall.startAnimation(fromBottomAnimation)
            binding.floatingActionButtonQr.startAnimation(fromBottomAnimation)
            binding.floatingActionButtonAdd.startAnimation(rotateOpenAnimation)
        } else {
            binding.floatingActionButtonCall.startAnimation(toBottomAnimation)
            binding.floatingActionButtonQr.startAnimation(toBottomAnimation)
            binding.floatingActionButtonAdd.startAnimation(rotateCloseAnimation)
        }
    }

    private fun buttonSetClickable() {
        if (!addButtonClicked) {
            binding.floatingActionButtonCall.isClickable = true
            binding.floatingActionButtonQr.isClickable = true
        } else {
            binding.floatingActionButtonCall.isClickable = false
            binding.floatingActionButtonQr.isClickable = false
        }
    }

}


class TabLayoutAdapter(fragment: FragmentManager, private var totalTabs: Int) :
    FragmentStatePagerAdapter(fragment) {
    private val fragmentTitleList = mutableListOf("Barchasi", "Qarzlar", "Haqlar")
    override fun getCount(): Int = totalTabs

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllDebtsFragment()
            }
            1 -> {
                DebtsFragment()
            }
            2 -> {
                FeesFragment()
            }
            else -> null!!
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }



}
