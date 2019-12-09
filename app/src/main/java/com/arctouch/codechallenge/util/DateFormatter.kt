package com.arctouch.codechallenge.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String  {
    val oldFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val newFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    return newFormat.format(oldFormat.parse(this))
}