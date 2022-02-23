package ru.iliavolkov.material.view.mainfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.tabs.TabLayout
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentMainBinding
import ru.iliavolkov.material.view.MainActivity
import ru.iliavolkov.material.view.chips.SettingFragment
import ru.iliavolkov.material.view.navigationDrawerFragment.BottomNavigationDrawerFragment
import ru.iliavolkov.material.viewmodel.PictureOfTheDayViewModel
import ru.iliavolkov.material.viewmodel.appstate.AppStatePictureOfTheDay

class MainFragment : Fragment() {

    private var isMain = true
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getPictureOfTheDay()
        clickInputLayout()
        clickFAB()
        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){}
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    private fun clickInputLayout() {
        binding.inputLayout.setStartIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data  = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun clickFAB() {
        binding.fab.setOnClickListener{
            if(isMain){
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageResource(R.drawable.ic_back_fab)
            }else{
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageResource(R.drawable.ic_plus_fab)
            }
            isMain = !isMain
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                Toast.makeText(requireContext(), "app_bar_fav", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment.newInstance(),"ChipsFragment").addToBackStack("").commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"BottomNavigationDrawerFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
//                binding.included.bottomSheetDescriptionHeader.text = it.pictureData.title
//                binding.included.bottomSheetDescription.text = it.pictureData.explanation
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
            requireActivity().onBackPressed()
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