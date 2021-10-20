package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.databinding.CardviewPersonalListBinding
import com.victor.myan.model.Anime

class PersonalListAnimeAdapter(private val btnRemove :  AppCompatButton) : ListAdapter<Anime, PersonalListAnimeAdapter.AnimeHolder>(MyDiffUtil) {

    val selectedList : MutableList<Int> = arrayListOf()
    var countSelectList : Int = 0

    companion object MyDiffUtil : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class AnimeHolder(binding: CardviewPersonalListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        val btnRemove = binding.btnRemove

        fun bind(anime: Anime) {
            Glide.with(itemView.context).load(anime.imageUrl).listener(object :
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

//            image.setOnClickListener {
//                Toast.makeText(itemView.context, anime.title, Toast.LENGTH_SHORT).show()
////                if(anime.checked) {
////                    var count = 0
////                    for (aux in 0 until selectedList.size) {
////                        if (anime.malID == selectedList[aux]) {
////                            count = aux
////                        }
////                    }
////                    btnRemove.visibility = View.INVISIBLE
////                    selectedList.removeAt(count)
////                }
//            }

//            image.setOnLongClickListener {
//                markSelectedAnime(anime.malID, btnRemove)
//            }
//
//            image.setOnClickListener {
//                if (btnRemove.isVisible) {
//                    var count = 0
//                    for (aux in 0 until selectedList.size) {
//                        if (anime.malID == selectedList[aux]) {
//                            count = aux
//                        }
//                    }
//                    btnRemove.visibility = View.INVISIBLE
//                    selectedList.removeAt(count)
//                    Log.e("Selectedlist", selectedList.toString())
//                } else {
//                    val fragment = BaseAnimeDetailFragment()
//                    val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager
//
//                    val bundle = Bundle()
//                    bundle.putInt("mal_id", anime.malID)
//
//                    fragment.arguments = bundle
//
//                    val transaction =
//                        fragmentManager?.
//                        beginTransaction()?.
//                        replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)?.
//                        addToBackStack(null)
//                    transaction?.commit()
//                    fragmentManager?.beginTransaction()?.commit()
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAnimeAdapter.AnimeHolder {
        return AnimeHolder(
            CardviewPersonalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListAnimeAdapter.AnimeHolder, position: Int) {
        val anime = getItem(position)
//        val btnRemove = holder.btnRemove

        holder.bind(anime)
        holder.itemView.setOnLongClickListener {
            btnRemove.visibility = View.VISIBLE
//            if(!selectedList.contains(anime.malID)){
//                selectedList.add(anime.malID)
//                btnRemove.visibility = View.VISIBLE
//                anime.checked = true
//                Log.e("Acrescentando: ", selectedList.size.toString())
//                Log.e("SelectList", selectedList.toString())
//            }
            true
        }
//        holder.itemView.setOnClickListener {
//            listener(anime)
//            if(anime.checked) {
//                var count = 0
//                for (aux in 0 until selectedList.size) {
//                    if (anime.malID == selectedList[aux]) {
//                        count = aux
//                    }
//                }
//                btnRemove.visibility = View.INVISIBLE
//                selectedList.removeAt(count)
//                anime.checked = false
//                Log.e("Retirando: ", selectedList.size.toString())
//                Log.e("SelectList", selectedList.toString())
//            }
//        }
    }
}