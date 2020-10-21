package com.philippeloctaux.epicture.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.philippeloctaux.epicture.R
import com.squareup.picasso.Picasso


class SinglePicture : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_picture)

        // setup up button to go back to previous activity
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // display image
        val image: ImageView = findViewById(R.id.imageView)
        Picasso.get()
//            .load("https://cannabis-seeds-usa.org/wp-content/uploads/2017/12/big-bud-marijuana-seeds-1_large.jpg")
            .load("https://licensedproducerscanada.ca/wp-content/uploads/2017/12/GhostGrow_DavinciOG-9437-300x300.png")
//            .load("https://cdn.cnn.com/cnnnext/dam/assets/191031084204-marijuana-flower-stock.jpg")
            .into(image)

        // fav / unfav button
        val favButton: ImageButton = findViewById(R.id.favButton)
        var fav = false // TODO: get actual value

        // initial value
        if (fav) {
            favButton.setImageResource(R.drawable.ic_baseline_favorite_32)
        } else {
            favButton.setImageResource(R.drawable.ic_baseline_favorite_border_32)
        }

        // when user clicks fav button
        favButton.setOnClickListener {
            fav = if (fav) {
                favButton.setImageResource(R.drawable.ic_baseline_favorite_border_32)
                // TODO: api call
                false
            } else {
                favButton.setImageResource(R.drawable.ic_baseline_favorite_32)
                // TODO: api call
                true
            }
        }

        // copy image url button
        val copyButton: ImageButton = findViewById(R.id.copyButton)
        copyButton.setImageResource(R.drawable.ic_baseline_content_copy_32)
        copyButton.setOnClickListener {
            // copy text to clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // TODO: replace sample text by actual value
            val clip = ClipData.newPlainText("Image URL", "image url goes here")
            clipboard.primaryClip = clip

            // display toast
            Toast.makeText(applicationContext, "Copied image URL to clipboard", Toast.LENGTH_SHORT)
                .show()
        }
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