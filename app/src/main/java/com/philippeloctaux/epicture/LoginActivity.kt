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

    private fun redirectToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()

        val settings = Settings(applicationContext)

        // TODO: if signed in check if access_token is still valid
        // TODO: else get a new one with refresh_token

        // if access_token is stored in Settings redirect to main activity
        if (!settings.getValue(settings.accessToken).isNullOrEmpty()) {
            redirectToMainActivity()
        }

        // if data is null return immediately
        val rawData = intent?.data ?: return

        // make sure callback uri is the one we expect
        if (!rawData.toString().startsWith(Constants.REDIRECT_URI)) {
            return Toast.makeText(this, "An unexpected error happened", Toast.LENGTH_LONG).show()
        }

        // parse uri
        val uri = Uri.parse(rawData.toString())
        if (uri == null) {
            return Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
        }

        // check if could not sign in
        val error: String? = uri.getQueryParameter("error")
        if (error != null) {
            // pretty error message
            val errorMessage = when (error) {
                "access_denied" -> "Access has been denied"
                else -> "Generic error"
            }
            return Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        // parse fragments
        // FIXME: can be null
        val fragments = parseUriFragment(uri.fragment)

        // store tokens in preferences
        fragments?.get(settings.accessToken)?.let { settings.setKeyValue(settings.accessToken, it) }
        fragments?.get(settings.expiresIn)?.let { settings.setKeyValue(settings.expiresIn, it) }
        fragments?.get(settings.tokenType)?.let { settings.setKeyValue(settings.tokenType, it) }
        fragments?.get(settings.refreshToken)
            ?.let { settings.setKeyValue(settings.refreshToken, it) }
        fragments?.get(settings.accountUsername)
            ?.let { settings.setKeyValue(settings.accountUsername, it) }
        fragments?.get(settings.accountId)?.let { settings.setKeyValue(settings.accountId, it) }

        // now we are signed in, redirect to main activity
        redirectToMainActivity()
    }
}