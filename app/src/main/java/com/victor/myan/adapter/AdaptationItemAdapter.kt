package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseAnimeFragment
import com.victor.myan.baseFragments.BaseMangaFragment
import com.victor.myan.databinding.CardviewRectangleBinding
import com.victor.myan.model.Adaptation

class AdaptationItemAdapter(private val adaptationList : List<Adaptation>) : RecyclerView.Adapter<AdaptationItemAdapter.AdaptationItemViewHolder>() {

    inner class AdaptationItemViewHolder(val binding : CardviewRectangleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptationItemViewHolder {
        return AdaptationItemViewHolder(CardviewRectangleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AdaptationItemAdapter.AdaptationItemViewHolder, position: Int) {
        val nameItem = holder.binding.itemNameText

        nameItem.text = adaptationList[position].name
        nameItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.black_gray))

        holder.itemView.setOnClickListener {
            when(adaptationList[position].type) {
                "manga" -> {
                    val fragment = BaseMangaFragment()
                    val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

                    val bundle = Bundle()
                    bundle.putInt("mal_id", adaptationList[position].malID)

                    fragment.arguments = bundle

                    val transaction =
                        fragmentManager?.
                        beginTransaction()?.
                        replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                    transaction?.commit()
                    fragmentManager?.beginTransaction()?.commit()
                }
                else -> {
                    val fragment = BaseAnimeFragment()
                    val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

                    val bundle = Bundle()
                    bundle.putInt("mal_id", adaptationList[position].malID)

                    fragment.arguments = bundle

                    val transaction =
                        fragmentManager?.
                        beginTransaction()?.
                        replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                    transaction?.commit()
                    fragmentManager?.beginTransaction()?.commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return adaptationList.size
    }
}