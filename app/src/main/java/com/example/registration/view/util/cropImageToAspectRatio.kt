package com.example.registration.view.util

import android.graphics.Bitmap
fun Bitmap.cropImageToAspectRatio( aspectRatio: Float): Bitmap {
    // Get original dimensions
    val originalWidth = width
    val originalHeight = height

    // Determine target dimensions based on aspect ratio
    val targetWidth: Int
    val targetHeight: Int

    if (originalWidth / originalHeight > aspectRatio) {
        // Image is wider than target aspect ratio
        targetHeight = originalHeight
        targetWidth = (targetHeight * aspectRatio).toInt()
    } else {
        // Image is taller than target aspect ratio
        targetWidth = originalWidth
        targetHeight = (targetWidth / aspectRatio).toInt()
    }

    // Calculate cropping coordinates
    val cropLeft = ((originalWidth - targetWidth) / 2).coerceAtLeast(0)
    val cropTop = ((originalHeight - targetHeight) / 2).coerceAtLeast(0)

    // Ensure cropping dimensions are within the bounds of the original bitmap
    val cropRight = (cropLeft + targetWidth).coerceAtMost(originalWidth)
    val cropBottom = (cropTop + targetHeight).coerceAtMost(originalHeight)

    // Create and return the cropped bitmap
    return Bitmap.createBitmap(
        this,
        cropLeft,
        cropTop,
        cropRight - cropLeft,
        cropBottom - cropTop
    )
}
