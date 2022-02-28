package ru.iliavolkov.material.view.main.setting

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                binding.chipDay.isChecked = true
            }
            "chipMars"->{
                binding.chipMars.isChecked = true
            }
            "chipMoon"->{
                binding.chipMoon.isChecked = true
            }
        }
    }

    private fun clickChip() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.chipDay->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipDay").apply()
                    requireActivity().setTheme(R.style.MyThemeDay)
                }
                R.id.chipMars->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMars").apply()
                    requireActivity().setTheme(R.style.MyThemeMars)
                }
                R.id.chipMoon->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMoon").apply()
                    requireActivity().setTheme(R.style.MyThemeMoon)
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