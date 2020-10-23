package com.philippeloctaux.epicture.utils

/**
 * gets image hash from imgur url
 * if not imgur image return null
 */
fun IsImgurImage(url: String): String? {
    val regex =
        Regex("(https?:)?//(\\w+\\.)?imgur\\.com/(\\S*)(\\.[a-zA-Z]{3})").find(url)
            ?: return null

    val (_, _, hash, _) = regex.destructured

    return hash
}