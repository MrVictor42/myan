package com.victor.myan.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.model.User

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
        }

        val navigationView = binding.bottomMenu
        val baseFragment = HomeFragment.newInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("users")
        val userID = user!!.uid

        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentUser = dataSnapshot.getValue(User::class.java)

                if (currentUser != null) {
                    Log.e("userprofile", currentUser.name)
                    Log.e("email", currentUser.email)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@BaseLayout, "Something Wrong Happened!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        addFragment(baseFragment)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    addFragment(HomeFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.search -> {
                    addFragment(SearchFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.categories -> {
                    addFragment(CategoriesListFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, FormLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            false
        }

        navigationView.setOnApplyWindowInsetsListener(null)
        navigationView.setPadding(0)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()
    }
}