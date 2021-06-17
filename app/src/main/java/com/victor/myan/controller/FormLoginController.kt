package com.victor.myan.controller

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.databinding.ActivityFormLoginControllerBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.layouts.BaseLayout


class FormLoginController : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginControllerBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

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