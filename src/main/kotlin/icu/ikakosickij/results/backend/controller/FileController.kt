package icu.ikakosickij.results.backend.controller

import icu.ikakosickij.results.backend.security.services.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/file")
class FileController {
    @Autowired
    private val fileService: FileService? = null
    @PostMapping("/upload")
    fun upload(@RequestParam("File") file: MultipartFile): ResponseEntity<*> {
        return ResponseEntity.ok<Any>(fileService!!.addFile(file))
    }

    @GetMapping("/download/{id}")
    fun download(@PathVariable id: String): ResponseEntity<ByteArrayResource> {
        val loadFile = fileService!!.downloadFile(id)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(loadFile.fileType!!))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${loadFile.filename}\"")
            .body(ByteArrayResource(loadFile.file!!))
    }
}