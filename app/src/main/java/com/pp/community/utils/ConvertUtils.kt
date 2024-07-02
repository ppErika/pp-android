package com.pp.community.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ConvertUtils {
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    fun dateToString(date: Date?): String? {
        if (date == null) {
            return null
        }
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(date)
    }
}