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
import androidx.appcompat.widget.AppCompatButton
import com.victor.myan.R
import com.victor.myan.databinding.FragmentCreateListBinding

class CreateListFragment : Fragment() {

    private lateinit var binding : FragmentCreateListBinding
    private lateinit var btnButtonImage : AppCompatButton
    private var uri : Uri? = null

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
            if(uri == null) {
                uri = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                            + resources.getResourcePackageName(R.drawable.chapeu) + '/'
                            + resources.getResourceTypeName((R.drawable.chapeu)) + '/'
                            + resources.getResourceEntryName(R.drawable.chapeu)
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            val circleImage = binding.circleImage
            circleImage.setImageBitmap(bitmap)
            btnButtonImage.alpha = 0f
        }
    }
}