package com.example.registration.view.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.registration.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.regex.Pattern

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

fun Uri.toBitmap(context: Context): Bitmap {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(this)
    return BitmapFactory.decodeStream(inputStream)
}


fun Bitmap.toUri(context: Context, fileName: String): Uri {
    val file = File(context.cacheDir, "$fileName.png")
    file.outputStream().use {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    return Uri.fromFile(file)
}

//populating spinner
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

fun String?.isNameValid(): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    return this.length < 3 || this.matches(
        Regex("^[a-zA-Z]+$")
    )
}

fun String?.isEmailValid(): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isPhoneNumberValid(): Boolean {
    if (this.isNullOrEmpty() || this.length < 10) {
        return false
    }
    return this.matches(Regex("^[0-9]{10}$"))
}

fun String?.isPasswordValid(): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    return Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
        .matcher(this).matches()
}

fun String?.isAddressValid(): Boolean {
    if (this.isNullOrEmpty()) return false

    return this.length < 3 || this.matches(
        Regex("^[a-zA-Z0-9]+$")
    )
}

fun String?.isPinCodeValid(): Boolean {
    if (this.isNullOrEmpty() || this.length < 6) {
        return false
    }
    return this.matches(Regex("^[0-9]{6}$"))
}

fun String?.isGradeValid(): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    return this.matches(Regex("^[A-D]$"))
}

fun String?.isExpValid(): Boolean {
    if (this.isNullOrEmpty()) {
        return false
    }
    return this.matches(Regex("^[0-9]{2}$"))
}
inline fun <reified T : Enum<T>> String.matchesFirstEnumConstant(): Boolean {
    return try {
        val firstEnum = T::class.java.enumConstants?.firstOrNull()
        firstEnum?.name == this
    } catch (e: Exception) {
        false
    }
}






