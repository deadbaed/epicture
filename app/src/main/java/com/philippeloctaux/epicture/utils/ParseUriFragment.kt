package com.philippeloctaux.epicture.utils

/**
 * parses a uri fragment
 * returns a hashmap of keys and values
 */
fun parseUriFragment(uri: String): Map<String, String>? {
    val map: MutableMap<String, String> = LinkedHashMap()
    val keys = uri.split("&").toTypedArray()

    for (key in keys) {
        val values = key.split("=").toTypedArray()
        map[values[0]] = if (values.size > 1) values[1] else ""
    }

    return map
}