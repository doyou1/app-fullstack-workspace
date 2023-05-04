package com.example.vocabularynote.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.*

class FileUtil {

    companion object {
        fun getFileExtension(context: Context, uri: Uri): String? {
            return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(context.contentResolver.getType(uri))
        }

        fun getWorkbook(context: Context, fileExtension: String, uri: Uri): Workbook? {
            return try {
                val fileName = "${Const.TEXT_TEMP}.${fileExtension}"
                val file = File(context.cacheDir, fileName)
                file.createNewFile()
                val fout = FileOutputStream(file)
                val fin = context.contentResolver.openInputStream(uri)
                if (fin != null) {
                    copy(fin, fout)
                    fout.flush()
                    WorkbookFactory.create(file)
                    // if error, replace
                    // val workbook = XSSFWorkbook(file)
                    // EmptyFileException
                    // val workbook = WorkbookFactory.create(fin)
                } else null
            } catch (e: Exception) {
                null
            }
        }

        @Throws(IOException::class)
        private fun copy(source: InputStream, target: OutputStream) {
            val buf = ByteArray(8192)
            var length: Int
            while (source.read(buf).also { length = it } > 0) {
                target.write(buf, 0, length)
            }
        }
    }

}