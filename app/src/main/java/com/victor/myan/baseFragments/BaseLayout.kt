package com.victor.myan.baseFragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.fragments.GenreFragment
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.fragments.SearchFragment
import com.victor.myan.screens.FormLoginActivity

class BaseLayout : AppCompatActivity() {

    private lateinit var binding: ActivityBaseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.BLACK
        }

        val navigationView = binding.bottomMenu
        val baseFragment = HomeFragment()

        addFragment(baseFragment)
        navigationView.setOnNavigationItemSelectedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_layout)
            when(it.itemId) {
                R.id.home -> {
                    if(fragment?.tag == "HomeFragment" &&
                        navigationView.menu.findItem(navigationView.selectedItemId).toString() == "Home") {
                        // Same fragment, nothing to do
                    } else {
                        addFragment(HomeFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                }

                R.id.search -> {
                    addFragment(SearchFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.genres -> {
                    addFragment(GenreFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.lists -> {
                    addFragment(BaseListsFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.settings -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, FormLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            false
        }

        navigationView.setOnApplyWindowInsetsListener(null)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()
    }
}