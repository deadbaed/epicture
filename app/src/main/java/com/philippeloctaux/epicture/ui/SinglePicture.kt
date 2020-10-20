package com.philippeloctaux.epicture.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
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

        val image: ImageView = findViewById(R.id.imageView)
        Picasso.get()
//            .load("https://cannabis-seeds-usa.org/wp-content/uploads/2017/12/big-bud-marijuana-seeds-1_large.jpg")
            .load("https://licensedproducerscanada.ca/wp-content/uploads/2017/12/GhostGrow_DavinciOG-9437-300x300.png")
//            .load("https://cdn.cnn.com/cnnnext/dam/assets/191031084204-marijuana-flower-stock.jpg")
            .into(image)
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