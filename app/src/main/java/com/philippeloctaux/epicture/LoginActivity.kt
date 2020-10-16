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

        // if data is null return immediately
        val rawData = intent?.data ?: return

        // make sure callback uri is the one we expect
        if (!rawData.toString().startsWith(Constants.REDIRECT_URI)) {
            Toast.makeText(this, "An unexpected error happened", Toast.LENGTH_LONG).show()
            return
        }

        // parse url
        val uri = Uri.parse(rawData.toString())

        // check if could not sign in
        val error: String? = uri.getQueryParameter("error")
        if (error != null) {
            // pretty error message
            val errorMessage = when (error) {
                "access_denied" -> "Access has been denied"
                else -> "Generic error"
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            return
        }

        // get access token n stuff
        // access_token
        // expires_in
        // token_type
        // refresh_token
        // account_username
        // account_id
    }
}