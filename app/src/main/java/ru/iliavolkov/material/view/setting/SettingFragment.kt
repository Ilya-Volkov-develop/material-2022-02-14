package ru.iliavolkov.material.view.setting

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChip()
        clickChip()

    }

    private fun initChip() {
        when(requireActivity().getPreferences(Activity.MODE_PRIVATE).getString("settingTheme","")){
            "chipDay"->{
                binding.chipDay.isCheckable = true
            }
            "chipNight"->{
                binding.chipNight.isCheckable = true
            }
            "chipMars"->{
                binding.chipMars.isCheckable = true
            }
            "chipMoon"->{
                binding.chipMoon.isCheckable = true
            }
        }
    }

    private fun clickChip() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.chipDay->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipDay").apply()
                    requireActivity().setTheme(R.style.MyThemeDay)
//                    recreate(requireActivity())
                }
                R.id.chipNight->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipNight").apply()
                    requireActivity().setTheme(R.style.MyThemeNight)
//                    recreate(requireActivity())
                }
                R.id.chipMars->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMars").apply()
                    requireActivity().setTheme(R.style.MyThemeMars)
//                    recreate(requireActivity())
                }
                R.id.chipMoon->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMoon").apply()
                    requireActivity().setTheme(R.style.MyThemeMoon)
//                    recreate(requireActivity())
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}