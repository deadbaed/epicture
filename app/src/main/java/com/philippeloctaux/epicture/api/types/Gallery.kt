package com.philippeloctaux.epicture.api.types

import java.io.Serializable

class Gallery(
    id: String?,
    title: String?,
    description: String?,
    favorite: Boolean?,
    link: String?,
    images: List<Image>?
) :
    Serializable {
    val id: String? = null
    val title: String? = null
    val description: String? = null
    val favorite: Boolean? = null
    val link: String? = null
    val images: List<Image>? = null
}

class GalleryResponse {
    val data: Gallery? = null
    val success: Boolean = false
    val status: Int? = null
}

class GalleryListResponse {
    val data: List<Gallery>? = null
    val success: Boolean = false
    val status: Int? = null
}
