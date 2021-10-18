package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.databinding.CardviewPersonalListBinding
import com.victor.myan.model.Anime

class PersonalListAnimeAdapter(private val animeList : MutableList<Anime>, private val listener : (Anime) -> Unit) : RecyclerView.Adapter<PersonalListAnimeAdapter.ViewHolder>() {

    inner class ViewHolder(binding: CardviewPersonalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        fun bind(anime: Anime) {
            Glide.with(itemView.context).load(anime.imageUrl).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAnimeAdapter.ViewHolder {
        return ViewHolder(
            CardviewPersonalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = animeList[position]
        holder.bind(anime)
        holder.itemView.setOnLongClickListener {
            listener(anime)
            true
        }
    }
}

//class PersonalListAnimeAdapter : ListAdapter<Anime, PersonalListAnimeAdapter.AnimeHolder>(MyDiffUtil){
//
//    private val selectedList : MutableList<Int> = arrayListOf()
//
//    companion object MyDiffUtil : DiffUtil.ItemCallback<Anime>() {
//        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
//            return oldItem.malID == newItem.malID
//        }
//    }
//
//    inner class AnimeHolder(binding: CardviewPersonalListBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        private val image = binding.image
//        private val btnRemove = binding.btnRemove
//
//        fun bind(anime: Anime) {
//            Glide.with(itemView.context).load(anime.imageUrl).listener(object :
//                RequestListener<Drawable> {
//                override fun onLoadFailed(e: GlideException?, model: Any?,
//                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
//                                          isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: com.bumptech.glide.request.target.Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//            }).into(image)
//
//            image.setOnLongClickListener {
//                markSelectedAnime(anime.malID, btnRemove)
//            }
//
//            image.setOnClickListener {
//                if(btnRemove.isVisible) {
//                    var count = 0
//                    for(aux in 0 until selectedList.size) {
//                        if(anime.malID == selectedList[aux]) {
//                            count = aux
//                        }
//                    }
//                    btnRemove.visibility = View.INVISIBLE
//                    selectedList.removeAt(count)
//                    Log.e("Selectedlist", selectedList.toString())
//                } else {
////                    val fragment = BaseAnimeDetailFragment()
////                    val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager
////
////                    val bundle = Bundle()
////                    bundle.putInt("mal_id", anime.malID)
////
////                    fragment.arguments = bundle
////
////                    val transaction =
////                        fragmentManager?.
////                        beginTransaction()?.
////                        replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)?.
////                        addToBackStack(null)
////                    transaction?.commit()
////                    fragmentManager?.beginTransaction()?.commit()
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAnimeAdapter.AnimeHolder {
//        return AnimeHolder(
//            CardviewPersonalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: PersonalListAnimeAdapter.AnimeHolder, position: Int) {
//        val anime = getItem(position)
//        holder.bind(anime)
//    }
//
//    private fun markSelectedAnime(malID: Int, btnRemove: AppCompatImageView): Boolean {
//        if(!selectedList.contains(malID)){
//            selectedList.add(malID)
//            btnRemove.visibility = View.VISIBLE
//        }
//        return true
//    }
//}