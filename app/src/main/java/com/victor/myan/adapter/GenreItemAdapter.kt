package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseGenreDetailFragment
import com.victor.myan.databinding.CardviewRectangleBinding
import com.victor.myan.enums.GenreEnum
import com.victor.myan.model.Genre

class GenreItemAdapter(private val genreList : List<Genre>) : RecyclerView.Adapter<GenreItemAdapter.GenreItemViewHolder>() {

    inner class GenreItemViewHolder(val binding : CardviewRectangleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        return GenreItemViewHolder(CardviewRectangleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        val genreType = holder.binding.itemNameText

        genreType.text = genreList[position].name

        when(genreList[position].name) {
            GenreEnum.Action.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            }
            GenreEnum.Adventure.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Cars.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Comedy.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_light))
            }
            GenreEnum.Dementia.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            GenreEnum.Demons.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Drama.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_light))
            }
            GenreEnum.Ecchi.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Fantasy.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Game.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Harem.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            GenreEnum.Historical.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Horror.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Josei.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Kids.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_blue))
            }
            GenreEnum.Magic.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_light))
            }
            GenreEnum.Mecha.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            GenreEnum.Military.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            GenreEnum.Music.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            GenreEnum.Mystery.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Parody.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_blue))
            }
            GenreEnum.Police.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Psychological.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            GenreEnum.Romance.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            GenreEnum.Samurai.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.School.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_blue))
            }
            GenreEnum.Seinen.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }
            GenreEnum.Shoujo.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            GenreEnum.Shounen.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Space.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_light))
            }
            GenreEnum.Sports.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_blue))
            }
            GenreEnum.Supernatural.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Thriller.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Vampire.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_light))
            }
            GenreEnum.Yaoi.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            GenreEnum.Yuri.name -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink))
            }
            else -> {
                genreType.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.black_gray))
            }
        }

        holder.itemView.setOnClickListener {
            val fragment = BaseGenreDetailFragment()
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val bundle = Bundle()
            bundle.putString("name", genreList[position].name)
            bundle.putInt("mal_id", genreList[position].malID)

            fragment.arguments = bundle

            val transaction =
                fragmentManager?.
                beginTransaction()?.
                replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            transaction?.commit()
            fragmentManager?.beginTransaction()?.commit()
        }
    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}