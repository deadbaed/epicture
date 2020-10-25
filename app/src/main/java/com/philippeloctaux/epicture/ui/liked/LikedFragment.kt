package com.philippeloctaux.epicture.ui.liked

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
import com.philippeloctaux.epicture.Settings
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.ImageListResponse
import com.philippeloctaux.epicture.utils.IsImgurImage
import retrofit2.Call
import retrofit2.Response

class LikedFragment : Fragment() {

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
        getFavoritesImages()

        return view
    }

    private fun getFavoritesImages() {
        val client = Imgur.create()
        val settings = Settings(this.requireContext())
        val apiRequest =
            client.getFavoritesImages("Bearer " + settings.getValue(settings.accessToken))

        apiRequest.enqueue(object : retrofit2.Callback<ImageListResponse> {
            override fun onResponse(
                call: Call<ImageListResponse>,
                response: Response<ImageListResponse>
            ) {
                val rawImageList = response.body()?.data
                val imageList = ArrayList<String>()

                if (rawImageList != null) {
                    for (image in rawImageList) {
                        if (image.link != null) {
                            if (IsImgurImage(image.link) != null) {
                                imageList.add(image.link)
                            }
                        }
                    }
                }

                // display images
                rv?.adapter = ListImages(requireContext(), imageList, false)
            }

            // on failure
            override fun onFailure(call: Call<ImageListResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Failed to get your favorite pictures",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}