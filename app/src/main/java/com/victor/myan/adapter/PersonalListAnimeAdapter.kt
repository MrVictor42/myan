package com.victor.myan.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseAnimeDetailFragment
import com.victor.myan.databinding.CardviewPersonalListBinding
import com.victor.myan.model.Anime

class PersonalListAnimeAdapter(
    private val animeList : MutableList<Anime>,
    private val btnRemove: AppCompatButton,
    private val listRef: DatabaseReference
) : RecyclerView.Adapter<PersonalListAnimeAdapter.AnimeHolder>() {

    private val selectedList : MutableList<Int> = arrayListOf()

    inner class AnimeHolder(binding: CardviewPersonalListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        private val iconRemove = binding.btnRemove

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind(anime: Anime) {
            Glide.with(itemView.context).load(anime.imageURL).listener(object :
            RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
                                          isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(image)

            if(!anime.checked) {
                iconRemove.visibility = View.INVISIBLE
                btnRemove.visibility = View.INVISIBLE
            }

            image.setOnClickListener {
                if(anime.checked) {
                    var count = 0
                    for (aux in 0 until selectedList.size) {
                        if(anime.malID == selectedList[aux]) {
                            count = aux
                        }
                    }
                    iconRemove.visibility = View.INVISIBLE
                    selectedList.removeAt(count)
                    anime.checked = false

                    if(selectedList.size == 0) {
                        btnRemove.visibility = View.INVISIBLE
                    } else {
                        btnRemove.text = "Remove ${ selectedList.size } Item From List"
                    }
                } else {
                    val fragment = BaseAnimeDetailFragment()
                    val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                    val bundle = Bundle()
                    bundle.putInt("mal_id", anime.malID)

                    fragment.arguments = bundle

                    val transaction =
                        fragmentManager?.
                        beginTransaction()?.
                        replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)?.
                        addToBackStack(null)
                    transaction?.commit()
                    fragmentManager?.beginTransaction()?.commit()
                }
            }

            image.setOnLongClickListener {
                if(!selectedList.contains(anime.malID)) {
                    selectedList.add(anime.malID)
                    anime.checked = true
                    iconRemove.visibility = View.VISIBLE
                    btnRemove.text = "Remove ${ selectedList.size } Item From List"
                    btnRemove.visibility = View.VISIBLE
                }
                true
            }

            iconRemove.setOnClickListener {
                var count = 0
                for (aux in 0 until selectedList.size) {
                    if(anime.malID == selectedList[aux]) {
                        count = aux
                    }
                }
                iconRemove.visibility = View.INVISIBLE
                selectedList.removeAt(count)
                anime.checked = false

                if(selectedList.size == 0) {
                    btnRemove.visibility = View.INVISIBLE
                } else {
                    btnRemove.text = "Remove ${ selectedList.size } Item From List"
                }
            }

            btnRemove.setOnClickListener {
                val alertBuilder = AlertDialog.Builder(itemView.context)
                alertBuilder.setTitle("Delete")
                alertBuilder.setMessage("Do you want to delete this item ?")
                alertBuilder.setPositiveButton("Delete"){_,_ ->
                    selectedList.forEach { malID ->
                        listRef.orderByKey().equalTo(malID.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (postSnapshot in snapshot.children) {
                                    postSnapshot.ref.removeValue()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("Error Firebase", "onCancelled", error.toException())
                            }
                        })
                    }
                    animeList.removeAll { anime ->
                        anime.checked
                    }
                    selectedList.clear()
                    Toast.makeText(itemView.context, "Anime deleted", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }

                alertBuilder.setNegativeButton("No"){_,_ ->

                }

                alertBuilder.setNeutralButton("Cancel"){_,_ ->

                }
                alertBuilder.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAnimeAdapter.AnimeHolder {
        return AnimeHolder(
            CardviewPersonalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListAnimeAdapter.AnimeHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}