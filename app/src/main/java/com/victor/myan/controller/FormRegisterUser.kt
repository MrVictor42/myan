package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.victor.myan.R
import com.victor.myan.Slides
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.model.User
import com.victor.myan.services.AuxServicesImpl

class FormRegisterUser : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private val auxServicesImpl = AuxServicesImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)

        binding.btnRegister.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val userName = binding.editUserName.text.toString()
            val message = binding.messageError

            message.text = auxServicesImpl.validateFields(userName, email, password)

            if(message.text == "") {
                val user = User()
                val userController = UserController()

                user.email = email
                user.password = password
                user.name = userName

                var valid: Boolean = userController.create(user, binding.layoutRegister)
                // POR ALGUM MOTIVO, ATE MESMO QUANDO NÃO REGISTRA, DA VALIDO E VOLTA PARA A LOGIN
                if(valid) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intentFormLogin = Intent(this, FormLoginController::class.java)
                        startActivity(intentFormLogin)
                        finish()
                    }, 2000)
                } else {

                }
            }
        }
    }
}
