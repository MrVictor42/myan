package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.model.User

class FormRegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private lateinit var textMessageError : AppCompatTextView
    private lateinit var progressBar : ProgressBar
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        textMessageError = binding.textMessageError
        textMessageError.visibility = View.GONE
        progressBar = binding.progressBar
        progressBar.visibility = View.GONE

        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if(!auxServicesHelper.validateField(name, binding.editTextName) &&
                !auxServicesHelper.validateField(email, binding.editTextEmail) &&
                !auxServicesHelper.validateField(password, binding.editTextPassword)) {
                return@setOnClickListener
            } else {
                createUser(email, password, name)
            }
        }
    }

    private fun createUser(email : String, password : String, name : String) {
        val mAuth = FirebaseAuth.getInstance()

        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
            if(it.isSuccessful) {
                val user = User()

                user.name = name
                user.email = email
                user.password = password
                user.userID = FirebaseAuth.getInstance().currentUser!!.uid

                FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Snackbar.make(
                            binding.activityFormRegisterUser,
                            auxServicesHelper.capitalize("the user was successfully registered!"),
                            Snackbar.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.GONE
                        FirebaseAuth.getInstance().signOut()

                        val intent = Intent(this, FormLoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Snackbar.make(
                            binding.activityFormRegisterUser,
                            auxServicesHelper.capitalize("failed to register! try again!"),
                            Snackbar.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.GONE
                    }
                }
            } else {
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    textMessageError.text =
                        auxServicesHelper.capitalize(
                            "insert a password with 6 no minimum characters!"
                        )
                is FirebaseAuthUserCollisionException ->
                    textMessageError.text =
                        auxServicesHelper.capitalize("this account already exists!")
                is FirebaseNetworkException ->
                    textMessageError.text =
                        auxServicesHelper.capitalize("without connection!")
                else ->
                    textMessageError.text =
                        auxServicesHelper.capitalize("${ it.message }!")
            }
            return@addOnFailureListener
        }
    }
}