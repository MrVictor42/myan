package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.helper.AuxFunctionsHelper

class FormRegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val message = binding.messageError

            message.text = auxServicesHelper.validateFields(email, password)

            if(message.text.isEmpty()) {
                createUser(email, password)
            }
        }
    }

    private fun createUser(email : String, password : String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(
                        this,
                        "the user was successfully registered!",
                        Toast.LENGTH_SHORT)
                        .show()
                    val intentFormLogin = Intent(this, FormLoginActivity::class.java)
                    startActivity(intentFormLogin)
                    finish()
                }, 2000)
            } else {
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            val messageError = binding.messageError
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    messageError.text =
                        auxServicesHelper.capitalize(
                            "insert a password with 6 no minimum characters!"
                        )
                is FirebaseAuthUserCollisionException ->
                    messageError.text =
                        auxServicesHelper.capitalize("this account already exists!")
                is FirebaseNetworkException ->
                    messageError.text =
                        auxServicesHelper.capitalize("without connection!")
                else ->
                    messageError.text =
                        auxServicesHelper.capitalize("${ it.message }!")
            }
            return@addOnFailureListener
        }
    }
}