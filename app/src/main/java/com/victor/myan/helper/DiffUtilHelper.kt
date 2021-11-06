package com.victor.myan.helper

import androidx.recyclerview.widget.DiffUtil
import com.victor.myan.model.Anime
import com.victor.myan.model.Jikan

class DiffUtilHelper(private val oldList : List<Jikan>, private val newList : List<Jikan>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].malID == newList[newItemPosition].malID
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].malID != newList[newItemPosition].malID -> false
            else -> true
        }
    }
}