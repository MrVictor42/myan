package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.model.User
import com.victor.myan.services.impl.AuxServicesImpl

class FormRegisterUserController : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private val auxServicesImpl = AuxServicesImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val message = binding.messageError

            message.text = auxServicesImpl.validateFields(email, password)

            if(message.text == "") {
                val user = User()
                val userController = UserController()

                user.email = email
                user.password = password

                userController.create(user, binding.layoutRegister)
                Handler(Looper.getMainLooper()).postDelayed({
                    val intentFormLogin = Intent(this, FormLoginController::class.java)
                    startActivity(intentFormLogin)
                    finish()
                }, 2000)
            }
        }
    }
}
