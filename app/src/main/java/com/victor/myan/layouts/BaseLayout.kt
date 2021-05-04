package com.victor.myan.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.fragments.SearchFragment

class BaseLayout : AppCompatActivity() {

    private lateinit var binding: ActivityBaseLayoutBinding
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                val homeFragment = HomeFragment.newInstance()
                addFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.search -> {
                val searchFragment = SearchFragment.newInstance()
                addFragment(searchFragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        toolbar.setTitle("Bem Vindo Sr. Victor")
        setSupportActionBar(toolbar)

        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val baseFragment = HomeFragment.newInstance()
        addFragment(baseFragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.
        beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
            .commit()
    }
}