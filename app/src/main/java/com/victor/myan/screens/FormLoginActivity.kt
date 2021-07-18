package com.victor.myan.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormLoginBinding
import com.victor.myan.helper.AuxFunctionsHelper

class FormLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
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
        val registerUserText = binding.registerUserText
        val messageError = binding.messageError

        btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                messageError.text = auxServicesHelper.capitalize("please, fill all fields")
            } else {
                authenticateUser(email, password)
            }
        }

        registerUserText.setOnClickListener {
            val formRegisterUserIntent = Intent(this, FormRegisterUserActivity::class.java)
            startActivity(formRegisterUserIntent)
        }
    }

    private fun authenticateUser(email: String, password: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                val intentLogin = Intent(this, BaseLayout::class.java)
                startActivity(intentLogin)
                finish()
            }
        }.addOnFailureListener {
            val messageError = binding.messageError
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