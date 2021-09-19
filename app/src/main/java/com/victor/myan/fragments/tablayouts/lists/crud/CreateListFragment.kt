package com.victor.myan.fragments.tablayouts.lists.crud

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.victor.myan.R
import com.victor.myan.databinding.FragmentCreateListBinding
import com.victor.myan.model.PersonalList
import java.util.UUID

class CreateListFragment : Fragment() {

    private lateinit var binding : FragmentCreateListBinding
    private lateinit var btnButtonImage : AppCompatButton
    private var selectedURI : Uri? = null

    companion object {
        fun newInstance(): CreateListFragment {
            val createListFragment = CreateListFragment()
            val args = Bundle()
            createListFragment.arguments = args
            return createListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnButtonImage = binding.btnSelectImage
        val btnButtonRegister = binding.btnRegister

        btnButtonImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnButtonRegister.setOnClickListener {
            if(selectedURI == null) {
                selectedURI = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                            + resources.getResourcePackageName(R.drawable.chapeu) + '/'
                            + resources.getResourceTypeName((R.drawable.chapeu)) + '/'
                            + resources.getResourceEntryName(R.drawable.chapeu)
                )
            }
            val file = UUID.randomUUID().toString()
            val reference = FirebaseStorage.getInstance().getReference("/list/images/${file}")
            selectedURI?.let {
                reference.putFile(it).addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        val url = it.toString()
                        val nameList = binding.nameList.text.trim().toString()
                        val descriptionList = binding.descriptionList.text.trim().toString()
                        val personalList = PersonalList()

                        personalList.url = url
                        personalList.name = nameList
                        personalList.description = descriptionList

                        FirebaseDatabase.getInstance().getReference("list")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(personalList).addOnCompleteListener {
                            if(it.isSuccessful) {
                                Toast.makeText(
                                    context, "The list ${personalList.name} was created with successful",
                                    Toast.LENGTH_SHORT)
                                .show()
                            } else {
                                Toast.makeText(
                                    context, "Something failed when save this list! Try again!",
                                    Toast.LENGTH_SHORT)
                                .show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null) {
            selectedURI = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedURI)
            val circleImage = binding.circleImage
            circleImage.setImageBitmap(bitmap)
            btnButtonImage.alpha = 0f
        }
    }
}