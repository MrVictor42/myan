package com.victor.myan.screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.enums.MessagesEnum

class FormRegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val toolbar = binding.toolbar

        window.statusBarColor = Color.WHITE
        toolbar.toolbar.setNavigationOnClickListener {
            val intent = Intent(this,FormLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val message = binding.messageError

            message.text = auxServicesHelper.validateFields(email, password)

            if(message.text.isEmpty()) {
                createUser(email, password)
                Handler(Looper.getMainLooper()).postDelayed({
                    val intentFormLogin = Intent(this, FormLoginActivity::class.java)
                    startActivity(intentFormLogin)
                    finish()
                }, 2000)
            }
        }
    }

    private fun createUser(email : String, password : String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                auxServicesHelper.message(binding.layoutRegister,
                    auxServicesHelper.capitalize(MessagesEnum.RegisterSuccessfully.message))
            } else {
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    auxServicesHelper.message(binding.layoutRegister,
                        auxServicesHelper.capitalize(MessagesEnum.PasswordWeak.message))
                is FirebaseAuthUserCollisionException ->
                    auxServicesHelper.message(binding.layoutRegister,
                        auxServicesHelper.capitalize(MessagesEnum.ExistsThisAccount.message))
                is FirebaseNetworkException ->
                    auxServicesHelper.message(binding.layoutRegister,
                        auxServicesHelper.capitalize(MessagesEnum.WithoutConnectionError.message))
                else ->
                    auxServicesHelper.message(binding.layoutRegister,
                        auxServicesHelper.capitalize("${ it.message }!"))
            }
            return@addOnFailureListener
        }
    }
}