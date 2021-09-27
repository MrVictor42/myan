package com.victor.myan.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout
import com.victor.myan.databinding.ActivityFormLoginBinding
import com.victor.myan.helper.AuxFunctionsHelper

class FormLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private lateinit var textMessageError : AppCompatTextView
    private lateinit var progressBar : ProgressBar
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        val btnLogin = binding.btnLogin
        val btnRegisterUser = binding.btnRegisterUser
        val btnForgotPassword = binding.btnForgotPassword

        textMessageError = binding.textMessageError
        progressBar = binding.progressBar

        textMessageError.visibility = View.GONE
        progressBar.visibility = View.GONE

        btnRegisterUser.setOnClickListener {
            val intent = Intent(this, FormRegisterUserActivity::class.java)
            startActivity(intent)
        }

        btnForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if(!auxServicesHelper.validateField(email, binding.editTextEmail) ||
                !auxServicesHelper.validateField(password, binding.editTextPassword)) {
                return@setOnClickListener
            } else {
                authenticateUser(email, password)
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        val progressBar = binding.progressBar

        progressBar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                val intent = Intent(this, BaseLayout::class.java)
                startActivity(intent)
                progressBar.visibility = View.GONE
                finish()
            } else {
                Snackbar.make(
                    binding.activityFormLogin,
                    auxServicesHelper.capitalize("failed to login! please check your credentials"),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }.addOnFailureListener {
            val messageError = binding.textMessageError
            when(it) {
                is FirebaseAuthInvalidCredentialsException ->
                    messageError.text =
                        auxServicesHelper.capitalize("email or password are incorrect!")
                is FirebaseNetworkException ->
                    messageError.text =
                        auxServicesHelper.capitalize(
                        "you haven't connection Wifi/4G in this moment, please able some " +
                                "connection and try again"
                        )
                else -> messageError.text = auxServicesHelper.capitalize("error login user!")
            }
        }
    }
}