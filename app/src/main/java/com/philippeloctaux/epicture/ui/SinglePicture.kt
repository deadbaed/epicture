package com.philippeloctaux.epicture.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.api.Constants
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.Image
import com.philippeloctaux.epicture.api.types.ImageResponse
import com.philippeloctaux.epicture.utils.IsImgurImage
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response


class SinglePicture : AppCompatActivity() {
    var image: Image? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_picture)

        // setup up button to go back to previous activity
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // get image hash from URL
        val imageURL = intent.getStringExtra("ImageURL")
        val hash = IsImgurImage(imageURL)!!

        // get & display image
        val im: ImageView = findViewById(R.id.imageView)
        val title: TextView = findViewById(R.id.imageTitle)
        val description: TextView = findViewById(R.id.description)
        val favButton: ImageButton = findViewById(R.id.favButton)
        getImage(hash, im, title, description, favButton)

        // fav / unfav button
        var fav = false // TODO: get actual value

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
            val clip = ClipData.newPlainText("Image URL", image?.link!!)
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

    private fun getImage(imageHash: String, im: ImageView, title: TextView, description: TextView, favButton: ImageButton) {
        val client = Imgur.create()
        val apiRequest =
            client.getImage("Client-ID " + Constants.CLIENT_ID, imageHash)

        // make request and wait for response
        apiRequest.enqueue(object : retrofit2.Callback<ImageResponse> {

            // on success
            override fun onResponse(
                call: Call<ImageResponse>,
                response: Response<ImageResponse>
            ) {
                // get json response
                image = response.body()?.data

                // display picture
                Picasso.get()
                    .load(image?.link!!)
                    .into(im)

                // display title
                if (image?.title == null) {
                    title.text = "untitled"
                } else {
                    title.text = image?.title!!
                }

                // display description
                if (image?.description == null) {
                    description.text = null
                } else {
                    description.text = image?.description!!
                }

                // favorite
                if (image?.favorite!!) {
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_32)
                } else {
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_border_32)
                }
            }

            // on failure
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to get picture", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}