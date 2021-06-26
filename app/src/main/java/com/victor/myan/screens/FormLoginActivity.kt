package com.victor.myan.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.databinding.ActivityFormLoginBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.messages.Messages

class FormLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnLogin = binding.btnLogin
        val registerUserText = binding.registerUserText
        val messageError = binding.messageError

        btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                messageError.text = auxServicesHelper.capitalize(Messages.FillAllFields.message)
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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                val intentLogin = Intent(this, BaseLayout::class.java)
                startActivity(intentLogin)
                finish()
            }
        }.addOnFailureListener {
            val messageError = binding.messageError

            when(it) {
                is FirebaseAuthInvalidCredentialsException -> messageError.text =
                    auxServicesHelper.capitalize(Messages.InvalidCredentials.message)
                is FirebaseNetworkException -> messageError.text =
                    auxServicesHelper.capitalize(Messages.WithoutConnection.message)
                else -> messageError.text = auxServicesHelper.capitalize(Messages.ErrorLoginUser.message)
            }
        }
    }
}