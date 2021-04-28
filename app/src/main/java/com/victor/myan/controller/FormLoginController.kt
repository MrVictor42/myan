package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormLoginControllerBinding
import com.victor.myan.services.AuxServicesImpl

class FormLoginController : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginControllerBinding
    private lateinit var auxServicesImpl: AuxServicesImpl
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)

        if(userController.userIsAuthenticated()) {

        } else {
            binding.btnLogin.setOnClickListener {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()

                if(email.isEmpty() || password.isEmpty()) {
                    binding.messageError.text = auxServicesImpl.capitalize("fill all fields!")
                } else {
                    authenticateUser(email, password)
                }
            }

            binding.registerUserText.setOnClickListener {
                val formRegisterUserIntent = Intent(this, FormRegisterUser::class.java)
                startActivity(formRegisterUserIntent)
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                auxServicesImpl.message(binding.layoutLogin, "successfully logged in!")
            }
        }.addOnFailureListener {
            val messageError = binding.messageError

            when(it) {
                is FirebaseAuthInvalidCredentialsException -> messageError.text =
                        auxServicesImpl.capitalize("email or password are incorrect!")
                is FirebaseNetworkException -> messageError.text =
                        auxServicesImpl.capitalize("without connection with internet!")
                else -> messageError.text = auxServicesImpl.capitalize("error login user!")
            }
        }
    }
}