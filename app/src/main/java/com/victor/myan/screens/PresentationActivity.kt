package com.victor.myan.screens

import android.content.Intent
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.victor.myan.R
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.enums.MessagesEnum

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
                .title(auxServicesHelper.capitalize(MessagesEnum.FirstPresentationTitle.message))
                .description(auxServicesHelper.capitalize(MessagesEnum.FirstPresentationDescription.message))
                .build()
        )

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.red)
                .image(R.drawable.luffy)
                .backgroundDark(R.color.red)
                .title(auxServicesHelper.capitalize(MessagesEnum.SecondPresentationTitle.message))
                .canGoBackward(true)
                .description(auxServicesHelper.capitalize(MessagesEnum.SecondPresentationDescription.message))
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