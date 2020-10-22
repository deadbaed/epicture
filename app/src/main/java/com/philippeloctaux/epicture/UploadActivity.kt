package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.ClipDescription
import android.graphics.Bitmap
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.ImageListResponse
import com.philippeloctaux.epicture.api.types.UploadResponse
import com.philippeloctaux.epicture.ui.upload.UploadFragment
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.fragment_upload.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.ArrayList

class UploadActivity : AppCompatActivity() {

    private var my_image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
//        File
        val image = intent.getStringExtra("Image")
        image_view.setImageURI(Uri.parse(image))
        val cancel_button: Button? = findViewById(R.id.button_cancel)
        val upload_button: Button? = findViewById(R.id.button_upload)
        val title: EditText ? = findViewById(R.id.editTitle)
        val description: EditText ? = findViewById(R.id.editDescription)

        cancel_button?.setOnClickListener {
            redirectToMainActivity()
        }
        upload_button?.setOnClickListener {
            val temp_title = title?.getText().toString()
            val temp_descr = description?.getText().toString()
            if (temp_title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            } else {
                UploadImage(image, temp_title, temp_descr)
            }
        }
    }

    private fun UploadImage(image: String, title: String, description: String) {
//        val byteA: ByteArrayOutputStream = ByteArrayOutputStream()
//        image?.compress(Bitmap.CompressFormat.PNG, 100, byteA)
//        val byteArray = byteA.toByteArray()
//        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
        val client = Imgur.create()
        val settings = Settings(applicationContext)
        val apiRequest =
            client.uploadImage("Bearer " + settings.getValue(settings.accessToken), image, title, description)

        // make request and wait for response
        apiRequest.enqueue(object : retrofit2.Callback<UploadResponse> {

            // on success
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                // return to home
                redirectToMainActivity()
            }

            // on failure
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to upload pictures", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    private fun redirectToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}