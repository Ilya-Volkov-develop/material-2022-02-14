@file:Suppress("DEPRECATION")

package ru.iliavolkov.material.view.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.iliavolkov.material.view.main.asteroid.AsteroidFragment
import ru.iliavolkov.material.view.main.earth.EarthFragment
import ru.iliavolkov.material.view.main.home.HomeFragment
import ru.iliavolkov.material.view.main.moon.MoonFragment
import ru.iliavolkov.material.view.main.setting.SettingFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(HomeFragment.newInstance(), AsteroidFragment.newInstance(), EarthFragment.newInstance(), MoonFragment.newInstance(), SettingFragment.newInstance())
    override fun getCount() = fragments.size
    override fun getItem(position: Int) = fragments[position]
}