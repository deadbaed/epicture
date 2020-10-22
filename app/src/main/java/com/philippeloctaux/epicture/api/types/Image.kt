package com.philippeloctaux.epicture.api.types

import java.io.Serializable

class Image(
    id: String?,
    title: String?,
    description: String?,
    favorite: Boolean?,
    type: String?,
    link: String?
) :
    Serializable {
    val id: String? = null
    val title: String? = null
    val description: String? = null
    val favorite: Boolean? = null
    val type: String? = null
    val link: String? = null
}

class ImageResponse {
    val data: Image? = null
    val success: Boolean = false
    val status: Int? = null
}

class ImageListResponse {
    val data: List<Image>? = null
    val success: Boolean = false
    val status: Int? = null
}
