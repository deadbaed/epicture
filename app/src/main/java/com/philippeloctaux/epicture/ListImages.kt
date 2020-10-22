package com.philippeloctaux.epicture

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.philippeloctaux.epicture.ui.SinglePicture
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_images.view.*

class ListImages(private val c: Context, private val images: ArrayList<String>) :
    RecyclerView.Adapter<ListImages.ColorViewHolder>() {


    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(c).inflate(R.layout.list_images, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val path = images[position]

        Picasso.get()
            .load(path)
            .resize(250, 250)
            .centerCrop()
            .into(holder.iv)

        holder.iv.setOnClickListener {
            val intent = Intent(c, SinglePicture::class.java).apply {
                putExtra("ImageURL", path)
            }
            startActivity(c, intent, null)
        }
    }

    class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv = view.iv as ImageView
    }
}