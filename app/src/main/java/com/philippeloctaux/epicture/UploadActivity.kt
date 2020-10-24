package com.philippeloctaux.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.loader.content.CursorLoader
import com.philippeloctaux.epicture.api.Imgur
import com.philippeloctaux.epicture.api.types.UploadResponse
import kotlinx.android.synthetic.main.activity_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadActivity : AppCompatActivity() {

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
                UploadImage(my_image, temp_title, temp_descr)
            }
        }
    }

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

        // TODO: check image type

        val type: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            "file"
        )
        val titleForm: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            title
        )
        val descriptionForm: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            description
        )

        val call =
            imgurApi.uploadImage("Bearer " + token, imageBody, type, titleForm, descriptionForm)
        println(call)

        call.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable?) {
                println("failed")
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                val res = response.body()

                println(res)

            }
        })
    }
}




