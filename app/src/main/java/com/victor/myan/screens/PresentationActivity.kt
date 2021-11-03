package com.victor.myan.screens

import android.content.Intent
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.victor.myan.R

class PresentationActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.yellow)
                .image(R.drawable.gohan)
                .backgroundDark(R.color.yellow_light)
                .title("Everything in same place!")
                .description("Discover various anime, manga, search for them and much more, " +
                        "all in one place!")
                .build()
        )

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.red)
                .image(R.drawable.luffy)
                .backgroundDark(R.color.red_light)
                .title("Every day, one new story!")
                .canGoBackward(true)
                .description("Follow the weekly anime, discover new stories and paths!!!")
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