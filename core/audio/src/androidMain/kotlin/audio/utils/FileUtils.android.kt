package audio.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import io.github.aakira.napier.Napier
import java.io.File
import java.io.FileOutputStream

fun Context.generateWavFile(prefix: String = RECORDING_PREFIX): File {
    val fileName = "$prefix${System.currentTimeMillis()}$RECORDING_EXTENSION"
    val outputFile = File(this.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)
    return outputFile
}

fun Context.savePickedAudioToAppStorage(uri: Uri): File? {
    val file = generateWavFile(prefix = IMPORTING_PREFIX)
    return try {
        this.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Context.getFileName(uri: Uri): String? {
    val cursor = this.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex >= 0) {
            return cursor.getString(nameIndex)
        }
    }
    return null
}

actual fun deleteFile(filePath: String): Boolean {
    Napier.d { "Deleting file: $filePath" }
    return try {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        } else {
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

actual fun fileExists(filePath: String): Boolean {
    return File(filePath).exists()
}