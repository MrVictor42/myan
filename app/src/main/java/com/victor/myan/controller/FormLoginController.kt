package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormLoginControllerBinding

class FormLoginController : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)

        binding.btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            // só pra deixar pronto, vai ter que passar por adaptações
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                        } else {

                        }
                    }
//            if(email.isEmpty()) {
//                binding.editEmail.setError("Name Required!")
//                binding.editEmail.requestFocus()
//                return@setOnClickListener
//            }
        }

        binding.registerUserText.setOnClickListener {
            val formRegisterUserIntent = Intent(this, FormRegisterUser::class.java)
            startActivity(formRegisterUserIntent)
        }
    }
}