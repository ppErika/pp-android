package com.pp.community.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object ConvertUtils {
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

}