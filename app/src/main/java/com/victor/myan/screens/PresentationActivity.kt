package com.victor.myan.screens

import android.content.Intent
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.victor.myan.R
import com.victor.myan.helper.AuxFunctionsHelper

class PresentationActivity : IntroActivity() {

    private val auxServicesHelper = AuxFunctionsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.yellow)
                .image(R.drawable.gohan)
                .backgroundDark(R.color.yellow)
                .title(auxServicesHelper.capitalize("everything in same place!"))
                .description(auxServicesHelper.capitalize(
                    "know the top anime and manga, search for them and much more, " +
                            "all in one place!!!"))
                .build()
        )

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.red)
                .image(R.drawable.luffy)
                .backgroundDark(R.color.red)
                .title(auxServicesHelper.capitalize("every day, one new story!"))
                .canGoBackward(true)
                .description(auxServicesHelper.capitalize(
                    "follow the weekly anime, discover new stories and paths!!!"))
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        val formLogin = Intent(this, FormLoginActivity::class.java)
        startActivity(formLogin)
        finish()
    }
}