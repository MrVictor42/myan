package com.victor.myan.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseAnimeFragment
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.helper.DiffUtilHelper
import com.victor.myan.model.Anime

class PersonalListAnimeAdapter(
    private val btnRemove: AppCompatButton,
    private val listRef: DatabaseReference
) : RecyclerView.Adapter<PersonalListAnimeAdapter.AnimeViewHolder>() {

    private var animeList : MutableList<Anime> = arrayListOf()
    private val selectedList : MutableList<Int> = arrayListOf()

    inner class AnimeViewHolder(val binding : CardviewPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val image = holder.binding.image
        val iconRemove = holder.binding.btnRemove
        val name = holder.binding.name

        Glide.with(holder.itemView.context).load(animeList[position].imageURL).listener(object :
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

        name.visibility = View.GONE

        image.setOnLongClickListener {
            if(!selectedList.contains(animeList[position].malID)) {
                selectedList.add(animeList[position].malID)
                animeList[position].checked = true
                iconRemove.visibility = View.VISIBLE
                btnRemove.text = "Remove ${ selectedList.size } Item From List"
                btnRemove.visibility = View.VISIBLE
            }
            true
        }

        image.setOnClickListener {
            if(btnRemove.visibility == View.VISIBLE && !animeList[position].checked) {
                selectedList.add(animeList[position].malID)
                animeList[position].checked = true
                iconRemove.visibility = View.VISIBLE
                btnRemove.text = "Remove ${ selectedList.size } Item From List"
                btnRemove.visibility = View.VISIBLE
            }
            else if(btnRemove.visibility == View.VISIBLE && animeList[position].checked) {
                var count = 0
                for (aux in 0 until selectedList.size) {
                    if(animeList[position].malID == selectedList[aux]) {
                        count = aux
                    }
                }
                iconRemove.visibility = View.INVISIBLE
                selectedList.removeAt(count)
                animeList[position].checked = false

                if(selectedList.size == 0) {
                    btnRemove.visibility = View.INVISIBLE
                } else {
                    btnRemove.text = "Remove ${ selectedList.size } Item From List"
                }
            } else {
                val fragment = BaseAnimeFragment(animeList[position].malID)
                val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

                val transaction =
                    fragmentManager?.
                    beginTransaction()?.
                    replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)?.
                    addToBackStack(null)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }

        iconRemove.setOnClickListener {
            if(animeList[position].checked) {
                var count = 0
                for (aux in 0 until selectedList.size) {
                    if(animeList[position].malID == selectedList[aux]) {
                        count = aux
                    }
                }
                iconRemove.visibility = View.INVISIBLE
                selectedList.removeAt(count)
                animeList[position].checked = false

                if(selectedList.size == 0) {
                    btnRemove.visibility = View.INVISIBLE
                } else {
                    btnRemove.text = "Remove ${ selectedList.size } Item From List"
                }
            }
        }

        btnRemove.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(holder.itemView.context)
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
                Snackbar.make(holder.binding.btnRemove, "Anime deleted...", Snackbar.LENGTH_LONG).show()
                notifyDataSetChanged()
                btnRemove.visibility = View.GONE
            }

            alertBuilder.setNegativeButton("No"){_,_ ->

            }

            alertBuilder.setNeutralButton("Cancel"){_,_ ->

            }
            alertBuilder.show()
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    fun setData(newAnimeList : MutableList<Anime>) {
        val diffUtil = DiffUtilHelper(animeList, newAnimeList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        animeList = newAnimeList
        diffResults.dispatchUpdatesTo(this)
    }
}