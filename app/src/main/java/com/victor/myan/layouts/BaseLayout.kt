package com.victor.myan.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.fragments.SearchFragment

class BaseLayout : AppCompatActivity() {

    private lateinit var binding: ActivityBaseLayoutBinding
    private var content: FrameLayout? = null
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when(item.itemId) {
            R.id.home -> {
                fragment = HomeFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.search -> {
                fragment = SearchFragment.newInstance()
                addFragment(fragment)
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
        toolbar.title = ""
        setSupportActionBar(toolbar)

        content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val baseFragment = HomeFragment()
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