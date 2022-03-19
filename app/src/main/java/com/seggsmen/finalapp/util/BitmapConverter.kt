package com.seggsmen.finalapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream


class BitmapConverter {
    companion object {
        fun convertBitmapToString(b: Bitmap, quality: Int): String {
            val base = ByteArrayOutputStream()

            b.compress(Bitmap.CompressFormat.WEBP, quality, base)

            val bytes = base.toByteArray()

            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }

        fun convertStringToBitmap(s: String): Bitmap {
            val bytes = Base64.decode(s, 0)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }
}