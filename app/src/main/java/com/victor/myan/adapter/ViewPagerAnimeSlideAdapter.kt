package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Anime

class ViewPagerAnimeSlideAdapter(val anime : MutableList<Anime>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.anime_slide_content, container, false)
        val animeImage = view.findViewById<ImageView>(R.id.anime_image_carousel)

        with(anime[position]) {
            Picasso.get().load(image_url).into(animeImage)
        }

        container.addView(view)
        return view
    }

    override fun getCount(): Int = anime.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}