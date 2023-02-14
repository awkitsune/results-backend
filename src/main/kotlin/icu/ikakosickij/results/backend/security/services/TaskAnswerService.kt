package icu.ikakosickij.results.backend.security.services

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.model.TaskAnswer
import icu.ikakosickij.results.backend.payload.response.MessageResponse
import icu.ikakosickij.results.backend.repository.TaskAnswerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.Valid

@Service
class TaskAnswerService {
    @Autowired
    var taskAnswerRepository: TaskAnswerRepository? = null

    fun getTaskAnswerList(): List<TaskAnswer> {
        val taskAnswerList: MutableList<TaskAnswer> = ArrayList()
        taskAnswerRepository!!.findAll().forEach{
            if (it != null) {
                taskAnswerList.add(it)
            }
        }
        return taskAnswerList
    }

    fun getTaskAnswerById(id: String): Optional<TaskAnswer> {
        return taskAnswerRepository!!.findById(id)
    }

    fun addItemToTheList(taskAnswer: TaskAnswer): String {
        taskAnswer.dateTime = Date()

        var id = ""
        taskAnswerRepository!!.save(taskAnswer)
        id = taskAnswer.id!!

        return id
    }

    fun deleteItem(id: String) {
        taskAnswerRepository!!.deleteById(id)
    }

    fun isTaskAnswerItemValid(id: String): Boolean {
        return taskAnswerRepository!!.existsById(id)
    }

    fun isTaskAnswerItemValid(userId: String, taskId: String): List<TaskAnswer> {
        return taskAnswerRepository!!.findByUserAndTaskId(userId, taskId)
    }
    fun getTaskAnswerListLength(): Long {
        return taskAnswerRepository!!.count()
    }

    fun updateTaskAnswerItem(id: String, taskAnswer: TaskAnswer): String {
        var updatedTaskAnswerId = ""
        try {
            val updatedTaskAnswer = taskAnswerRepository!!.findById(id).get()
            updatedTaskAnswer.mark = taskAnswer.mark
            taskAnswerRepository!!.save(updatedTaskAnswer)
            updatedTaskAnswerId = updatedTaskAnswer.id!!

            return updatedTaskAnswerId
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return updatedTaskAnswerId
    }

}