package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.databinding.ActivityFormLoginControllerBinding
import com.victor.myan.layouts.BaseLayout
import com.victor.myan.helper.AuxFunctionsHelper

class FormLoginController : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginControllerBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                binding.messageError.text = auxServicesHelper.capitalize("fill all fields!")
            } else {
                authenticateUser(email, password)
            }
        }

        binding.registerUserText.setOnClickListener {
            val formRegisterUserIntent = Intent(this, FormRegisterUserController::class.java)
            startActivity(formRegisterUserIntent)
        }
    }

    private fun authenticateUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                auxServicesHelper.message(binding.layoutLogin, "successfully logged in!")
                val intentLogin = Intent(this, BaseLayout::class.java)
                startActivity(intentLogin)
                finish()
            }
        }.addOnFailureListener {
            val messageError = binding.messageError

            when(it) {
                is FirebaseAuthInvalidCredentialsException -> messageError.text =
                    auxServicesHelper.capitalize("email or password are incorrect!")
                is FirebaseNetworkException -> messageError.text =
                    auxServicesHelper.capitalize("without connection with internet!")
                else -> messageError.text = auxServicesHelper.capitalize("error login user!")
            }
        }
    }
}