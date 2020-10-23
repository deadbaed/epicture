package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // setup up button to go back to previous activity
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

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
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
            }
            println("le titre est $temp_title et la description est $temp_descr")
        }
    }

    private fun redirectToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // up button goes to previous fragment instead to default fragment in parent activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}