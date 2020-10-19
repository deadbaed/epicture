package com.philippeloctaux.epicture.ui.upload

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Fragment
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.philippeloctaux.epicture.R
import java.util.jar.Manifest

class UploadFragment : Fragment() {



    private lateinit var uploadViewModel: UploadViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_upload, container, false)

        if (this.context?.checkPermission("READ_EXTERNAL_STORAGE", 1, 1) != PERMISSION_GRANTED ) {
            requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 23)
        }
        if (this.context?.checkPermission("WRITE_EXTERNAL_STORAGE", 1, 1) != PERMISSION_GRANTED ) {
            requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), 23)
        }
        return root
    }

}