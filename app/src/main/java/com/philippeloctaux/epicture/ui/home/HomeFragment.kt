package com.philippeloctaux.epicture.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.philippeloctaux.epicture.ListImages
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.api.Constants
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.GalleryListResponse
import com.philippeloctaux.epicture.utils.IsImgurImage
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class HomeFragment : Fragment() {

    var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // list of images
        rv = view.findViewById(R.id.rv)
        val imagesPerRow = 3
        rv?.layoutManager =
            StaggeredGridLayoutManager(imagesPerRow, StaggeredGridLayoutManager.VERTICAL)

        // get images from imgur homepage
        getHomePage()

        return view
    }

    private fun getHomePage() {
        val client = Imgur.create()
        val apiRequest =
            client.getHomePage("Client-ID " + Constants.CLIENT_ID)

        // make request and wait for response
        apiRequest.enqueue(object : retrofit2.Callback<GalleryListResponse> {

            // on success
            override fun onResponse(
                call: Call<GalleryListResponse>,
                response: Response<GalleryListResponse>
            ) {
                // get json response
                val rawGalleryList = response.body()?.data
                val imageList = ArrayList<String>()

                if (rawGalleryList != null) {
                    for (gallery in rawGalleryList) {
                        if (gallery.images != null) {
                            for (image in gallery.images) {
                                // add url of each image to list
                                if (image.link != null) {
                                    if (IsImgurImage(image.link) != null) {
                                        imageList.add(image.link)
                                    }
                                }
                            }
                        }
                    }
                }

                // display images
                rv?.adapter = ListImages(requireContext(), imageList, false)
            }

            // on failure
            override fun onFailure(call: Call<GalleryListResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Failed to get images from home page",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}