package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Staff

class StaffAdapter(val staff : MutableList<Staff>) : RecyclerView.Adapter<StaffAdapter.StaffHolder>() {

    class StaffHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image : ImageView = itemView.findViewById(R.id.list_image_adapter)

        fun bind(staff : Staff) {
            Picasso.get().load(staff.image_url).placeholder(R.drawable.placeholder).fit().into(image)

            image.setOnClickListener {
                Toast.makeText(itemView.context, staff.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image_adapter, parent, false)
        return StaffHolder(view)
    }

    override fun onBindViewHolder(holder: StaffHolder, position: Int) {
        holder.bind(staff[position])
    }

    override fun getItemCount(): Int {
        return staff.size
    }
}