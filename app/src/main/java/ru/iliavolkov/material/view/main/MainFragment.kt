package ru.iliavolkov.material.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MainViewPagerAdapter(requireActivity().supportFragmentManager)
        initNavigation()
    }


    private fun initNavigation() {
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
                    1 -> binding.bottomNavigationView.menu.findItem(R.id.navigation_favourite).isChecked = true
                    2 -> binding.bottomNavigationView.menu.findItem(R.id.navigation_weather).isChecked = true
                    3 -> binding.bottomNavigationView.menu.findItem(R.id.navigation_settings).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.navigation_favourite -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.navigation_weather -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.navigation_settings -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                else -> true
            }
        }
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.navigation_home -> {
//                    true
//                }
//                R.id.navigation_favourite -> {
//                    true
//                }
//                R.id.navigation_weather -> {
//                    true
//                }
//                R.id.navigation_settings -> {
//                    true
//                }
//                else -> true
//            }
//        }
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