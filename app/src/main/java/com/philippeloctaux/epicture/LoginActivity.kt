package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.philippeloctaux.epicture.api.Constants

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.signin_button).setOnClickListener {
            val authURL =
                "https://api.imgur.com/oauth2/authorize?client_id=" + Constants.CLIENT_ID + "&response_type=" + Constants.RESPONSE_TYPE + "&state=" + Constants.APPLICATION_STATE
            val uri = Uri.parse(authURL)
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            ContextCompat.startActivity(this, browserIntent, null)
        }
    }

    override fun onResume() {
        super.onResume()

        val test: Uri? = intent?.data
        if (test != null) {
            Toast.makeText(this, "works", Toast.LENGTH_SHORT).show()
        }
    }
}