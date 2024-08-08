package com.example.registration.view.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.registration.R
import java.io.InputStream

// crop image
fun Bitmap.cropImageToAspectRatio(aspectRatio: Float): Bitmap {
    val originalWidth = width
    val originalHeight = height

    val targetWidth: Int
    val targetHeight: Int

    if (originalWidth / originalHeight > aspectRatio) {
        targetHeight = originalHeight
        targetWidth = (targetHeight * aspectRatio).toInt()
    } else {
        targetWidth = originalWidth
        targetHeight = (targetWidth / aspectRatio).toInt()
    }

    val cropLeft = ((originalWidth - targetWidth) / 2).coerceAtLeast(0)
    val cropTop = ((originalHeight - targetHeight) / 2).coerceAtLeast(0)

    val cropRight = (cropLeft + targetWidth).coerceAtMost(originalWidth)
    val cropBottom = (cropTop + targetHeight).coerceAtMost(originalHeight)

    return Bitmap.createBitmap(
        this, cropLeft, cropTop, cropRight - cropLeft, cropBottom - cropTop
    )
}


//getBitmapFromUri
fun Uri.getBitmapFromUri(context : Context): Bitmap {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this)
    return BitmapFactory.decodeStream(inputStream)
}


//extension foe populating spinner
fun <T : Enum<T>> Spinner.populateSpinner(
    context: Context,
    enumClass: Class<T>,
    selectedItem: Int = 0,
    onEnumValueSelected: (enumValue: T) -> Unit
) {
    val enumValues = enumClass.enumConstants ?: return
    val adapterEnum = object : ArrayAdapter<T>(
        context,
        android.R.layout.simple_spinner_item,
        enumValues
    ) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent) as TextView
            customizeView(view, position)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent) as TextView
            customizeView(view, position)
            return view
        }

        private fun customizeView(view: TextView, position: Int) {
            val item = getItem(position)?.toString() ?: return
            if (item == displayItemSelector(enumValues.find { it.name == "NONE" }!!)) {
                view.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.hintColor
                    )
                )
                view.setTypeface(view.typeface, Typeface.ITALIC)
            } else {
                view.setTextColor(ContextCompat.getColor(context, R.color.black))
                view.typeface = Typeface.DEFAULT
            }
        }

        private fun displayItemSelector(item: T): String {
            return item.toString()
        }
    }.apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    this.adapter = adapterEnum
    this.setSelection(selectedItem, false)

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (position == 0) return
            val selectedEnumValue = enumValues[position]
            onEnumValueSelected(selectedEnumValue)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}



