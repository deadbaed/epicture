package com.philippeloctaux.epicture.ui.upload

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_upload.*
import android.widget.Toast
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.philippeloctaux.epicture.LoginActivity
import com.philippeloctaux.epicture.MainActivity
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.UploadActivity
import kotlinx.android.synthetic.main.fragment_upload.*
import java.util.jar.Manifest


class UploadFragment : Fragment() {


    private lateinit var uploadViewModel: UploadViewModel

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_SELECTED_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_TAKE_CODE)
    }

    companion object {
        private val IMAGE_SELECTED_CODE = 1000;
        private val PERMISSION_CODE = 1001;
        private val IMAGE_TAKE_CODE = 1002;
        var image_uri: Uri? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_upload, container, false)
        val g_button: Button? = root.findViewById(R.id.gallery_button)
        val c_button: Button? = root.findViewById(R.id.camera_button)

        if (this.context?.checkPermission("WRITE_EXTERNAL_STORAGE", 1, 1) != PERMISSION_GRANTED) {
            requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
        }
        g_button?.setOnClickListener {
            pickImageFromGallery()
        }
        c_button?.setOnClickListener {
            openCamera()
        }
        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val IMAGE = "Image"
            val intent = Intent(this.requireContext(), UploadActivity::class.java)
            if (requestCode == IMAGE_TAKE_CODE) {
//                intent.apply { putExtra(IMAGE, image_uri.toString()) }
                intent.apply { putExtra(IMAGE, image_uri) }
            }
            if (requestCode == IMAGE_SELECTED_CODE) {
//                intent.apply { putExtra(IMAGE, data?.data.toString()) }
                intent.apply { putExtra(IMAGE, data?.data) }
            }
            startActivity(intent)
        }
    }
}