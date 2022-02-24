package ru.iliavolkov.material.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.iliavolkov.material.R
import ru.iliavolkov.material.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(getPreferences(Activity.MODE_PRIVATE).getString("settingTheme","")){
            "chipDay"->{
                setTheme(R.style.MyThemeDay)
            }
            "chipNight"->{
                setTheme(R.style.MyThemeNight)
            }
            "chipMars"->{
                setTheme(R.style.MyThemeMars)
            }
            "chipMoon"->{
                setTheme(R.style.MyThemeMoon)
            }
        }


        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}