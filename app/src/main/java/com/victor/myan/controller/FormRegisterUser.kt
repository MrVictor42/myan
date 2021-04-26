package com.victor.myan.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.model.User
import com.victor.myan.services.impl.UserServicesImpl
import java.util.*

class FormRegisterUser : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private var selectedURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = this.resources.getColor(R.color.white)

        binding.btnImageUser.setOnClickListener {
            selectPhotoFromGallery()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val userName = binding.editUserName.text.toString()
            val message = binding.messageError

            if(userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                message.text = "Please, Fill All Fields!"
            } else {
                Toast.makeText(this, "AAAAAAAA", Toast.LENGTH_LONG).show()
                message.text = ""
//                val user = User()
//                val userServicesImpl = UserServicesImpl()
//                userServicesImpl.save(user)

            }
        }
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0) {
            selectedURI = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedURI)
            binding.imageUser.setImageBitmap(bitmap)
            binding.btnImageUser.alpha = 0f
        }
    }
}
