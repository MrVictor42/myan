package com.victor.myan.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseFragmentLayoutBinding

class BaseFragmentLayout : AppCompatActivity() {

    private lateinit var binding: ActivityBaseFragmentLayoutBinding
    private var content: FrameLayout? = null
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                val homeFragment = HomeFragment.newInstance()
                addFragment(homeFragment)
            }

            R.id.search -> {
                val searchFragment = SearchFragment.newInstance()
                addFragment(searchFragment)
            }

            R.id.your_list -> {
                val yourListFragment = ListsFragment.newInstance()
                addFragment(yourListFragment)
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseFragmentLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val baseFragment = HomeFragment.newInstance()
        addFragment(baseFragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.
            beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}