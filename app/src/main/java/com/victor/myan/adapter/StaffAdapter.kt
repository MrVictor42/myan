package com.victor.myan.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.model.Anime
import com.victor.myan.model.Staff

class StaffAdapter(var staff: MutableList<Staff>) :
    RecyclerView.Adapter<StaffAdapter.StaffHolder>() {

    class StaffHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StaffHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return staff.size
    }
}