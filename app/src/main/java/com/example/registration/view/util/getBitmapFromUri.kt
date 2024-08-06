package com.example.registration.view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

fun Uri.getBitmapFromUri(context : Context): Bitmap {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this)
    return BitmapFactory.decodeStream(inputStream)
}