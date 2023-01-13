package com.accountbook.backend

import jakarta.servlet.annotation.MultipartConfig
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.Log
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
    fun upload(@RequestParam("name") name: Object, @RequestPart images: Array<MultipartFile>): Int {
        println("name: $name")
        println("images: $images")
//        for (item in request.parts) {
//            if (item.contentType == "image/jpg; charset=utf-8") {
//                println("item.name: ${item.name}")
//                println("item.contentType: ${item.contentType}")
//                println("item.size: ${item.size}")
//                println("item.headerNames: ${item.headerNames}")
//                println("item.inputStream: ${item.inputStream}")
//                println("item.submittedFileName: ${item.submittedFileName}")
//                println("=====================================")
//
//                val fout = FileOutputStream(File(item.submittedFileName))
//                item.inputStream.transferTo(fout)
//                fout.close()
//            }
//        }

        return -1
    }

    @RequestMapping("/api/home", method = [RequestMethod.GET])
    fun home(): String {
        println("home")
        return "home"
    }
}