package com.example.registration.view.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.example.registration.R
import com.google.android.material.imageview.ShapeableImageView
import java.io.ByteArrayOutputStream

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun ShapeableImageView.load(byteArray: ByteArray?){
    if (byteArray==null) return
    Glide.with(context)
        .load(byteArrayToBitmap(byteArray))
        .error(R.drawable.ic_person)
        .into(this)
}
