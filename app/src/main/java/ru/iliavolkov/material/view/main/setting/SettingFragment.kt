package ru.iliavolkov.material.view.main.setting

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private var flag = false
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
                    textMovement()
                }
                R.id.chipMars->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMars").apply()
                    requireActivity().setTheme(R.style.MyThemeMars)
                    textMovement()
                }
                R.id.chipMoon->{
                    requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putString("settingTheme","chipMoon").apply()
                    requireActivity().setTheme(R.style.MyThemeMoon)
                    textMovement()
                }
            }
        }
    }

    private fun textMovement() {
            flag = !flag
            if(flag){
                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(),R.layout.fragment_setting)
                val changeBounds = ChangeBounds()
                changeBounds.duration = 1000
                changeBounds.interpolator = AnticipateOvershootInterpolator()
                TransitionManager.beginDelayedTransition(binding.settingContainer,changeBounds)
                constraintSet.applyTo(binding.settingContainer)
            }else{
                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(),R.layout.fragment_setting)
                constraintSet.connect(R.id.textMovement,ConstraintSet.START,R.id.settingContainer,ConstraintSet.START)
                /*constraintSet.connect(R.id.title,ConstraintSet.START,R.id.constraint_container,ConstraintSet.START)
                constraintSet.connect(R.id.title,ConstraintSet.TOP,R.id.constraint_container,ConstraintSet.TOP)*/

                val changeBounds = ChangeBounds()
                changeBounds.duration = 1000
                changeBounds.interpolator = AnticipateOvershootInterpolator()
                TransitionManager.beginDelayedTransition(binding.settingContainer,changeBounds)
                constraintSet.applyTo(binding.settingContainer)
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