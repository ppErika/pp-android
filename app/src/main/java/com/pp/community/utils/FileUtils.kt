package com.pp.community.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.pp.domain.model.post.PreSignedUploadUrlTemp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    @SuppressLint("Range")
    fun getFileInfo(context: Context, type: String, uri: Uri): PreSignedUploadUrlTemp {
        var fileName = "unknown"
        var fileSize: Long = 0
        var fileType: String? = context.contentResolver.getType(uri)

        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
                if (sizeIndex != -1) {
                    fileSize = it.getLong(sizeIndex)
                }
            }
        }

        // Create a temporary file if file size or name is unknown
        if (fileName == "unknown" || fileSize == 0L) {
            val tempFile = createTempFileFromUri(context, uri)
            fileName = tempFile.name
            fileSize = tempFile.length()
        }

        // Get file extension
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        // Get MIME type from file extension
        if (fileType == null && fileExtension != null) {
            fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase())
        }

        if (fileType == null) {
            fileType = "unknown"
        }

        return PreSignedUploadUrlTemp(
            fileType = type,
            fileName = fileName,
            fileContentLength = fileSize,
            fileContentType = fileType,
            filePath = uri.path ?: ""
        )
    }

    fun createTempFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, "tempFile_" + System.currentTimeMillis())
        val outputStream = FileOutputStream(tempFile)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return tempFile
    }

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = inputStream?.use {
            BitmapFactory.decodeStream(it)
        }

        bitmap?.let {
            val exif = ExifInterface(context.contentResolver.openInputStream(uri)!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            return rotateBitmap(it, orientation)
        }
        return bitmap
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
            else -> return bitmap
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun compressBitmap(bitmap: Bitmap, maxSizeInBytes: Int): ByteArray {
        var quality = 100
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        while (stream.toByteArray().size > maxSizeInBytes && quality > 0) {
            quality -= 5
            stream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        }

        return stream.toByteArray()
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val aspectRatio = width.toFloat() / height.toFloat()
        var newWidth = maxWidth
        var newHeight = maxHeight

        if (width > height) {
            newHeight = (newWidth / aspectRatio).toInt()
        } else if (height > width) {
            newWidth = (newHeight * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    fun saveBitmapToTempFile(context: Context, bitmap: Bitmap): Uri {
        val tempFile = File.createTempFile("compressed_", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return Uri.fromFile(tempFile)
    }
}
