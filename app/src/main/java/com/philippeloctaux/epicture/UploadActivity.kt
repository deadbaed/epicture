package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.app.Fragment
import android.content.ClipDescription
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.loader.content.CursorLoader
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.philippeloctaux.epicture.R
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.ImageListResponse
import com.philippeloctaux.epicture.api.types.UploadResponse
import com.philippeloctaux.epicture.ui.upload.UploadFragment
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.fragment_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.ArrayList
import java.util.Base64.getEncoder

class UploadActivity : AppCompatActivity() {

//    private var my_image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // setup up button to go back to previous activity
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val my_image = intent.getParcelableExtra<Uri>("Image") //getStringExtra("test")
        image_view.setImageURI(my_image)
        val cancel_button: Button? = findViewById(R.id.button_cancel)
        val upload_button: Button? = findViewById(R.id.button_upload)
        val title: EditText? = findViewById(R.id.editTitle)
        val description: EditText? = findViewById(R.id.editDescription)

        cancel_button?.setOnClickListener {
            redirectToMainActivity()
        }
        upload_button?.setOnClickListener {
            val temp_title = title?.getText().toString()
            val temp_descr = description?.getText().toString()
            if (temp_title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
            } else {
//                UploadImage(image, temp_title, temp_descr)
                UploadImage(my_image, temp_title, temp_descr)
            }
        }
    }

/*    private fun UploadImage(image: String, title: String, description: String) {

        // convert an image : string to Bitmap.
        val imageBytes = Base64.decode(image, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        // Convert a image : Bitmap to Base64.
        val test: ByteArrayOutputStream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, test)
        val b = test.toByteArray()
        val resImage = Base64.encodeToString(b, Base64.DEFAULT)

        // Call the Upload with the Image.
        val client = Imgur.create()
        val settings = Settings(applicationContext)

        // TODO: uncomment and rewrite this
        // -phil
        *//*
        val apiRequest = client.uploadImage(
            "Bearer " + settings.getValue(settings.accessToken),
            resImage,
            title,
            description
        ).also {

            // make request and wait for response
            it.enqueue(object : Callback<UploadResponse> {

                // on success
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {
//                    // Check the response to the Upload.
//                    if (response.body()?.success == null) {
//                        Toast.makeText(applicationContext, "response == NULL", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                    else if (response.body()?.success == false) {
//                        Toast.makeText(applicationContext, "Failed to upload pictures to imgur", Toast.LENGTH_SHORT)
//                                .show()
//                    }
//                    else {
                        // return to home
                        redirectToMainActivity()
//                    }
                }

                // on failure
                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed to upload pictures", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } *//*

    }*/

    private fun redirectToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // up button goes to previous fragment instead to default fragment in parent activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(applicationContext, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = cursor?.getString(column_index!!)
        cursor?.close()
        return result
    }

    fun UploadImage(image: Uri, title: String, description: String) {
        val imgurApi = Imgur.create()
//        val imgurApi = RetrofitService().createImgurService()
        val settings = Settings(applicationContext)
//    val path = image.path
        val token = settings.getValue(settings.accessToken)
        val file = File(getRealPathFromURI(image!!))
        val requestFile = RequestBody.create(MediaType.parse(contentResolver.getType(image)), file)
        val imageBody = MultipartBody.Part.createFormData("image", file.toString(), requestFile)

        // Upload une image sans titre ni description
        val call = imgurApi.uploadOneFile("Bearer " + token, imageBody)
//        val call = imgurApi.uploadImage("Bearer " + token, imageBody, ????, title, description)

        call.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable?) {}
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()
                    println(res)

                } else {
                    val res = response.body()
                    println(res)
                }
            }
        })
    }
}




