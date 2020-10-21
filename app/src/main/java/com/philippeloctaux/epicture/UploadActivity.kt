package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.philippeloctaux.epicture.R
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.fragment_upload.*

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
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
            }
            println("le titre est $temp_title et la description est $temp_descr")
        }
    }

    private fun redirectToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}