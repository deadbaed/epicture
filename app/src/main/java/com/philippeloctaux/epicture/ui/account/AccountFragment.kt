package com.philippeloctaux.epicture.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.philippeloctaux.epicture.ListImages
import com.philippeloctaux.epicture.LoginActivity
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.Settings
import com.squareup.picasso.Picasso

class AccountFragment : Fragment() {
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

        val sglm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val rv: RecyclerView = view.findViewById(R.id.rv)
        rv.layoutManager = sglm

        val imageList = ArrayList<String>()
        imageList.add("https://cdn.cnn.com/cnnnext/dam/assets/191031084204-marijuana-flower-stock.jpg")
        imageList.add("https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1200&h=1200.0&fit=crop")
        imageList.add("https://cdn.cnn.com/cnnnext/dam/assets/191031084204-marijuana-flower-stock.jpg")
        imageList.add("https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1200&h=1200.0&fit=crop")
        imageList.add("https://cdn.cnn.com/cnnnext/dam/assets/191031084204-marijuana-flower-stock.jpg")
        imageList.add("https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1200&h=1200.0&fit=crop")
        val igka = ListImages(this.requireContext(), imageList)
        rv.adapter = igka

        return view
    }
}