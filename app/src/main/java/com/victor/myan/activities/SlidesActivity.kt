package com.victor.myan.activities

import android.content.Intent
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.victor.myan.R
import com.victor.myan.controller.FormLoginController
import com.victor.myan.services.impl.AuxServicesImpl

class SlidesActivity : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        val auxServicesImpl = AuxServicesImpl()

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.yellow)
                .image(R.drawable.gohan)
                .backgroundDark(R.color.yellow)
                .title(auxServicesImpl.capitalize("everything in same place!"))
                .description(auxServicesImpl.capitalize("follow The animes of the season, " +
                        "search for animes from previous seasons, all in one place!!"))
                .build()
        )

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.red)
                .image(R.drawable.luffy)
                .backgroundDark(R.color.red)
                .title(auxServicesImpl.capitalize("every day, one new story!"))
                .canGoBackward(true)
                .description("follow the weekly anime, discover new stories " +
                        "and paths!")
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        val formLogin = Intent(this, FormLoginController::class.java)
        startActivity(formLogin)
        finish()
    }
}