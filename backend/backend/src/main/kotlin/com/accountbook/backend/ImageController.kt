package com.accountbook.backend

import jakarta.servlet.annotation.MultipartConfig
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.Log
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@RestController
@RequestMapping("/api")
class ImageController {

    @RequestMapping("/get", method = [RequestMethod.POST])
    fun get(
        @RequestBody names: List<String>
    ): HashMap<String, ByteArray> {

        println(names)

        return hashMapOf()
    }

    @RequestMapping("/upload", method = [RequestMethod.POST])
    fun upload(
        request: HttpServletRequest
    ): Int {
        val dirPath ="${getAbsolutePath()}/backend/src/main/resources/images"
        val dir = File(dirPath)
        if(!dir.exists()) {
            dir.mkdir()
        }

        for (item in request.parts) {
            if (item.contentType == "image/jpg; charset=utf-8") {
                val file = File("$dirPath/${item.submittedFileName}")
                val fout = FileOutputStream(file)
                item.inputStream.transferTo(fout)
                fout.close()
            }
        }

        return -1
    }

    private fun getAbsolutePath() : String {
        return File("").absolutePath
    }
}