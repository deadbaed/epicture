package com.philippeloctaux.epicture.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.philippeloctaux.epicture.LoginActivity
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.Settings
import com.redmadrobot.acronymavatar.AvatarView

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
        settings.getValue(settings.accountUsername)?.let {
            view.findViewById<AvatarView>(R.id.user_avatar).setText(
                it
            )
        }

        // display username
        val username: TextView = view.findViewById(R.id.username)
        username.text = settings.getValue(settings.accountUsername)

        // sign out
        val signOut : Button = view.findViewById(R.id.sign_out)
        signOut.setOnClickListener {
            // clear settings
            settings.clear()

            // redirect to login activity
            startActivity(Intent(this.requireContext(), LoginActivity::class.java))
        }

        return view
    }
}