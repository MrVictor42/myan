package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.PersonalListAddRemoveBinding
import com.victor.myan.model.PersonalList

class PersonalListAddRemoveAdapter : ListAdapter<PersonalList, PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<PersonalList>() {
        override fun areItemsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem.ID == newItem.ID
        }
    }

    inner class PersonalListAddRemoveHolder(binding : PersonalListAddRemoveBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.namePersonalList

        fun bind(personalList: PersonalList) {
            name.text = personalList.name

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, personalList.name, Toast.LENGTH_SHORT).show()
//                val fragment = BaseListDetailFragment()
//                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager
//
//                val bundle = Bundle()
//                bundle.putString("ID", personalList.ID)
//                bundle.putString("description", personalList.description)
//                bundle.putString("image", personalList.image)
//                bundle.putString("name", personalList.name)
//
//                fragment.arguments = bundle
//
//                val transaction =
//                    fragmentManager?.
//                    beginTransaction()?.
//                    replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
//                transaction?.commit()
//                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder {
        return PersonalListAddRemoveHolder(
            PersonalListAddRemoveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder, position: Int) {
        val personalList = getItem(position)
        holder.bind(personalList)
    }
}