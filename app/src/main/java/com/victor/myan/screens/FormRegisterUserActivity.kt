package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.victor.myan.R
import com.victor.myan.databinding.ActivityFormRegisterUserBinding
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.model.User

class FormRegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormRegisterUserBinding
    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if(!auxServicesHelper.validateField(name, binding.editName) &&
                !auxServicesHelper.validateField(email, binding.editEmail) &&
                !auxServicesHelper.validateField(password, binding.editPassword)) {
                return@setOnClickListener
            } else {
                createUser(email, password, name)
            }
        }
    }

    private fun createUser(email : String, password : String, name : String) {
        val mAuth = FirebaseAuth.getInstance()
        val progressBar = binding.progressBarRegister

        progressBar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
            if(it.isSuccessful) {
                val user = User()

                user.name = name
                user.email = email
                user.userID = FirebaseAuth.getInstance().currentUser!!.uid

                FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Toast.makeText(this,
                            auxServicesHelper.capitalize("the user was successfully registered!"),
                            Toast.LENGTH_SHORT)
                        .show()
                        progressBar.visibility = View.GONE
                        val intent = Intent(this, FormLoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,
                            auxServicesHelper.capitalize("failed to register! try again!"),
                            Toast.LENGTH_SHORT)
                            .show()
                        progressBar.visibility = View.GONE
                    }
                }
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