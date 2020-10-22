package com.philippeloctaux.epicture.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.philippeloctaux.epicture.ListImages
import com.philippeloctaux.epicture.LoginActivity
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.Settings
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.ImageListResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class AccountFragment : Fragment() {

    var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // get settings
        val settings = Settings(this.requireContext())

        // set avatar
        val username = settings.getValue(settings.accountUsername).toString()
        val avatarUrl = "https://imgur.com/user/$username/avatar"
        val userAvatar: ImageView = view.findViewById(R.id.user_avatar)
        Picasso.get()
            .load(avatarUrl)
            .placeholder(
                AvatarGenerator.avatarImage(
                    this.requireContext(),
                    128,
                    AvatarConstants.CIRCLE,
                    username
                )
            )
            .into(userAvatar)

        // display username
        val usernameView: TextView = view.findViewById(R.id.username)
        usernameView.text = settings.getValue(settings.accountUsername)

        // sign out
        val signOut: Button = view.findViewById(R.id.sign_out)
        signOut.setOnClickListener {
            // clear settings
            settings.clear()

            // redirect to login activity
            startActivity(Intent(this.requireContext(), LoginActivity::class.java))
        }

        // list of images
        rv = view.findViewById(R.id.rv)
        val imagesPerRow = 3
        rv?.layoutManager =
            StaggeredGridLayoutManager(imagesPerRow, StaggeredGridLayoutManager.VERTICAL)

        getAccountImages()

        return view
    }

    private fun getAccountImages() {
        val client = Imgur.create()
        val settings = Settings(this.requireContext())
        val apiRequest =
            client.getAccountImages("Bearer " + settings.getValue(settings.accessToken))

        // make request and wait for response
        apiRequest.enqueue(object : retrofit2.Callback<ImageListResponse> {

            // on success
            override fun onResponse(
                call: Call<ImageListResponse>,
                response: Response<ImageListResponse>
            ) {
                // get json response
                val rawImageList = response.body()?.data
                val imageList = ArrayList<String>()

                if (rawImageList != null) {
                    for (image in rawImageList) {
                        // add url of each image to list
                        imageList.add(image.link!!)
                    }
                }

                // display images
                rv?.adapter = ListImages(requireContext(), imageList)
            }

            // on failure
            override fun onFailure(call: Call<ImageListResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to get pictures", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}