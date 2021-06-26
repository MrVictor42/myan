package com.victor.myan.screens

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.fragments.CategoryFragment
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.fragments.SearchFragment

class BaseLayout : AppCompatActivity() {

    private lateinit var binding: ActivityBaseLayoutBinding
    private var content: FrameLayout? = null
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragment : Fragment?
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

            R.id.catalog -> {
                fragment = CategoryFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.loggout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, FormLoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val baseFragment = HomeFragment.newInstance()
        addFragment(baseFragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.
        beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()
    }
}