package com.victor.myan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class Slides : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            SimpleSlide.Builder()
                    .background(R.color.yellow)
                    .image(R.drawable.gohan)
                    .backgroundDark(R.color.yellow)
                    .title("Everything In Same Place!")
                    .description("Follow The Animes of The Season, Search For Animes " +
                            "From Previous Seasons, All in One Place!!")
                    .build()
        )

        addSlide(
                SimpleSlide.Builder()
                        .background(R.color.red)
                        .image(R.drawable.luffy)
                        .backgroundDark(R.color.red)
                        .title("Every Week, One New Story!")
                        .canGoBackward(true)
                        .description("Follow The Weekly Animes, Discover New Stories " +
                                "And Paths!")
                        .build()
        )
    }
}