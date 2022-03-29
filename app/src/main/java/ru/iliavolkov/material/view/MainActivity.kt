package ru.iliavolkov.material.view

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.ActivityMainBinding
import ru.iliavolkov.material.view.main.MainFragment
import ru.iliavolkov.material.view.notes.NotesFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null){
//            val fragmentTag = supportFragmentManager.findFragmentByTag("MainFragment")
//            if(fragmentTag==null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
//            }
        }
    }

    //Устанавиоиваем начальную тему приложения
    private fun setCustomTheme() {
        when(getPreferences(Activity.MODE_PRIVATE).getString("settingTheme", "")){
            "chipDay" -> {
                setTheme(R.style.MyThemeDay)
            }
            "chipMars" -> {
                setTheme(R.style.MyThemeMars)
            }
            "chipMoon" -> {
                setTheme(R.style.MyThemeMoon)
            }
        }
    }

    //Инициализачия toolbar
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(binding.toolbar)
        initDrawer(toolbar)
    }

    //Инициализация DrawerLayout
    private fun initDrawer(toolbar: Toolbar) {
        try {
            val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.app_name, R.string.app_name)
            binding.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
//                    R.id.navigation_home -> {
//                        val fragmentTag = supportFragmentManager.findFragmentByTag("MainFragment")
//                        if (fragmentTag == null) {
//                            binding.drawerLayout.closeDrawers()
//                            supportFragmentManager.beginTransaction()
//                                    .replace(R.id.container, MainFragment.newInstance(), "MainFragment")
//                                    .addToBackStack("")
//                                    .commit()
//                        }
//                        return@setNavigationItemSelectedListener true
//                    }
                    R.id.navigation_notes -> {

                        val fragmentTag = supportFragmentManager.findFragmentByTag("NotesFragment")
                        if (fragmentTag == null) {
                            binding.drawerLayout.closeDrawers()
                            supportFragmentManager.beginTransaction()
                                    .add(R.id.container, NotesFragment.newInstance(), "NotesFragment")
                                    .addToBackStack("")
                                    .commit()
                        }
                        return@setNavigationItemSelectedListener true
                    }
                }
                false
            }
            //setNavigationItems()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    private fun setNavigationItems() {
        binding.navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragmentTag = supportFragmentManager.findFragmentByTag("MainFragment")
                    if (fragmentTag == null) {
                        binding.drawerLayout.closeDrawers()
                        supportFragmentManager.beginTransaction()
                                .add(R.id.container, MainFragment.newInstance(), "MainFragment")
                                .addToBackStack("")
                                .commit()
                    }
                    return@setNavigationItemSelectedListener true
                }
                R.id.navigation_notes -> {

                    val fragmentTag = supportFragmentManager.findFragmentByTag("NotesFragment")
                    if (fragmentTag == null) {
                        binding.drawerLayout.closeDrawers()
                        supportFragmentManager.beginTransaction()
                                .add(R.id.container, NotesFragment.newInstance(), "NotesFragment")
                                .addToBackStack("")
                                .commit()
                    }
                    return@setNavigationItemSelectedListener true
                }
            }
            false
        }
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) binding.drawerLayout.close()
        else super.onBackPressed()
    }
}