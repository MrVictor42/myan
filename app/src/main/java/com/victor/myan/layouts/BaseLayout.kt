package com.victor.myan.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.controller.FormLoginController
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.fragments.CatalogFragment
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

            R.id.catalog -> {
                fragment = CatalogFragment.newInstance()
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

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLoginController::class.java)
            startActivity(intent)
            finish()
        }

        binding.contact.setOnClickListener {
            Toast.makeText(this,
                "Aqui vai ficar para o cidadão mandar mensagem para o desenvolvedor",
                Toast.LENGTH_SHORT).show()
        }

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