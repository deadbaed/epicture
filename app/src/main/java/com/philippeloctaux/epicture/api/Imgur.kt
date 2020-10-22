package com.philippeloctaux.epicture.api

import com.philippeloctaux.epicture.api.types.ImageListResponse
import com.philippeloctaux.epicture.api.types.UploadResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Imgur {
    companion object {
        fun create(): Imgur {
            val builder = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
            val retrofit = builder.build()
            return retrofit.create(Imgur::class.java)
        }
    }

    @GET("account/me/images")
    fun getAccountImages(@Header("Authorization") accessToken: String): Call<ImageListResponse>

    @FormUrlEncoded
    @POST("upload")
    fun uploadImage(
        @Header("Authorization") accessToken: String,
        @Field("image") image: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<UploadResponse>
}