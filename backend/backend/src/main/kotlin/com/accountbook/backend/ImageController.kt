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
    fun get(): String {

        return "load"
    }

    @RequestMapping("/upload", method = [RequestMethod.POST])
    fun upload(
        @RequestPart("id") id: String,
        request: HttpServletRequest
    ): Int {
        val dirPath ="${getAbsolutePath()}/backend/src/main/resources/images/$id"
        val dir = File(dirPath)
        if(!dir.exists()) {
            dir.mkdir()
        }

        for (item in request.parts) {
            if (item.contentType == "image/jpg; charset=utf-8") {
                val file = File("$dirPath/${item.submittedFileName}.jpg")
                val fout = FileOutputStream(file)
                item.inputStream.transferTo(fout)
                fout.close()
            }
        }

        return -1
    }

    @RequestMapping("/api/home", method = [RequestMethod.GET])
    fun home(): String {
        println("home")
        return "home"
    }

    private fun getAbsolutePath() : String {
        return File("").absolutePath
    }
}