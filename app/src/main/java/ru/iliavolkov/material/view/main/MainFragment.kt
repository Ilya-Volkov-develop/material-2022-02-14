package ru.iliavolkov.material.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentMainBinding
import ru.iliavolkov.material.viewmodel.PictureOfTheDayViewModel
import ru.iliavolkov.material.viewmodel.appstate.AppStatePictureOfTheDay
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getPictureOfTheDay(takeDate(0))
        clickInputLayout()
        tabLayoutInit()
        bottomSheet()
    }

    private fun bottomSheet() {
        behavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun tabLayoutInit() {
        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{viewModel.getPictureOfTheDay(takeDate(0))}
                    1->{viewModel.getPictureOfTheDay(takeDate(-1))}
                    2->{viewModel.getPictureOfTheDay(takeDate(-2))}
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    private fun clickInputLayout() {
        binding.inputLayout.setStartIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data  = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment.newInstance(),"ChipsFragment").addToBackStack("").commit()

    private fun renderData(it: AppStatePictureOfTheDay?) {
        when(it){
            is AppStatePictureOfTheDay.Error -> {
                loadingFailed(it.error,it.code)
            }
            is AppStatePictureOfTheDay.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStatePictureOfTheDay.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.customImageView.load(it.pictureData.url)
                binding.included.bottomSheetDescriptionHeader.text = it.pictureData.title
                binding.included.bottomSheetDescription.text = it.pictureData.explanation
            }
        }
    }

    //при ошибке всплывает диалоговое окно
    private fun loadingFailed(textId: Int, code: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater? = LayoutInflater.from(requireContext())
        val exitView: View = inflater!!.inflate(R.layout.dialog_error, null)
        dialog.setView(exitView)
        val dialog1: Dialog? = dialog.create()
        val ok: Button = exitView.findViewById(R.id.ok)
        val codeTextView = exitView.findViewById<TextView>(R.id.textError)

        codeTextView.text = when(textId) {
            R.string.errorOnServer -> getString(R.string.errorOnServer)
            R.string.codeError -> getString(R.string.codeError) + " " + code
            else -> ""
        }
        dialog1?.setCancelable(false)
        ok.setOnClickListener {
            dialog1?.dismiss()
        }
        dialog1?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}