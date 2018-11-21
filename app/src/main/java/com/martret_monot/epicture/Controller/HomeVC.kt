package com.martret_monot.epicture.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.martret_monot.epicture.Controller.Fragment.GalleryFragment
import com.martret_monot.epicture.Controller.Fragment.UploadFragment
import com.martret_monot.epicture.Controller.Fragment.HomeFragment
import com.martret_monot.epicture.Controller.Fragment.SettingsFragment
import com.martret_monot.epicture.R

class HomeVC : AppCompatActivity() {

    lateinit var selectedFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_vc)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        setFragment(HomeFragment())
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {

            R.id.nav_home -> {
                selectedFragment = HomeFragment()
                setFragment(selectedFragment)

               return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favorite -> {
                selectedFragment = UploadFragment()
                setFragment(selectedFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_gallery -> {
                selectedFragment = GalleryFragment()
                setFragment(selectedFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                selectedFragment = SettingsFragment()
                setFragment(selectedFragment)

                return@OnNavigationItemSelectedListener true
            }
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        }
        false
    }

    private fun setFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
    }
}
