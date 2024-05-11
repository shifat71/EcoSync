package com.homo_sapiens.ecosync.util.extension

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.homo_sapiens.ecosync.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


fun Context.shareAppLink() {
    try {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Share Application")
            val appLink =
                "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            putExtra(Intent.EXTRA_TEXT, appLink)
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.getImageUri(bitmap: Bitmap?): Uri {
    if (bitmap == null) return Uri.EMPTY
    val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
         "${System.currentTimeMillis()}.png")
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 80, out)
    out.close()
    return file.toUri()
}