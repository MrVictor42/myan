package com.victor.myan.baseFragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.databinding.ActivityBaseLayoutBinding
import com.victor.myan.model.User
import com.victor.myan.screens.FormLoginActivity
import com.victor.myan.screens.GenresListFragment
import com.victor.myan.fragments.HomeFragment
import com.victor.myan.screens.SearchFragment

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
                    if(navigationView.menu.findItem(navigationView.selectedItemId).toString() == "Home") {
                        // Same fragment, nothing to do
                    } else {
                        addFragment(HomeFragment.newInstance())
                        return@setOnNavigationItemSelectedListener true
                    }
                }

                R.id.search -> {
                    addFragment(SearchFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.genres -> {
                    addFragment(GenresListFragment.newInstance())
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
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()
    }
}