package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.philippeloctaux.epicture.api.Constants
import com.philippeloctaux.epicture.utils.parseUriFragment

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

        // parse uri
        val uri = Uri.parse(rawData.toString())
        if (uri == null) {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
            return
        }

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

        // parse fragments
        // FIXME: can be null
        val fragments = parseUriFragment(uri.fragment)

        // access_token
        val access_token = fragments?.get("access_token")
        println("access_token $access_token")

        // expires_in
        val expires_in = fragments?.get("expires_in")
        println("expires_in $expires_in")

        // token_type
        val token_type = fragments?.get("token_type")
        println("token_type $token_type")

        // refresh_token
        val refresh_token = fragments?.get("refresh_token")
        println("refresh_token $refresh_token")

        // account_username
        val account_username = fragments?.get("account_username")
        println("account_username $account_username")

        // account_id
        val account_id = fragments?.get("account_id")
        println("account_id $account_id")

    }
}