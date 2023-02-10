package icu.ikakosickij.results.backend.security.services

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import icu.ikakosickij.results.backend.model.LoadFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsOperations
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class FileService {
    @Autowired
    private val template: GridFsTemplate? = null

    @Autowired
    private val operations: GridFsOperations? = null

    fun addFile(upload: MultipartFile): String {
        val metadata: DBObject = BasicDBObject()
        metadata.put("fileSize", upload.size)
        val fileID: Any = template!!.store(upload.inputStream, upload.originalFilename, upload.contentType, metadata)
        return fileID.toString()
    }

    fun downloadFile(id: String?): LoadFile {
        val gridFSFile = template?.findOne(Query(Criteria.where("_id").`is`(id)))
        val loadFile = LoadFile()
        if (gridFSFile?.metadata != null) {
            loadFile.filename = gridFSFile.filename
            loadFile.fileType = gridFSFile.metadata!!["_contentType"].toString()
            loadFile.fileSize = gridFSFile.metadata!!["fileSize"].toString()
            loadFile.file = operations!!.getResource(gridFSFile).inputStream.readAllBytes()
        }
        return loadFile
    }
}