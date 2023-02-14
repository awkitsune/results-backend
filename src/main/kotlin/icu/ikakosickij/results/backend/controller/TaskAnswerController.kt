package icu.ikakosickij.results.backend.controller

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.model.TaskAnswer
import icu.ikakosickij.results.backend.payload.request.LoginRequest
import icu.ikakosickij.results.backend.payload.request.TaskAnswerExistenceCheckRequest
import icu.ikakosickij.results.backend.payload.response.MessageResponse
import icu.ikakosickij.results.backend.security.services.TaskAnswerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/task/answer")
class TaskAnswerController {
    @Autowired
    var taskAnswerService: TaskAnswerService? = null

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: String): Optional<TaskAnswer> {
        return taskAnswerService!!.getTaskAnswerById(id)
    }

    @PostMapping("/add")
    fun addItemToList(@Valid @RequestBody taskAnswer: TaskAnswer): ResponseEntity<*> {
        var id = taskAnswerService!!.addItemToTheList(taskAnswer)

        return if (id.isNotEmpty()){
            ResponseEntity.ok<Any>(
                MessageResponse("Added task successfully"))
        } else {
            ResponseEntity.badRequest().body<Any>(
                MessageResponse("Failed task addition"))
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    fun taskAnswerList(): List<TaskAnswer> {
        return taskAnswerService!!.getTaskAnswerList()
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteItem(@PathVariable id: String): ResponseEntity<*> {
        return if (taskAnswerService!!.isTaskAnswerItemValid(id)) {
            taskAnswerService!!.deleteItem(id)

            ResponseEntity.ok<Any>(
                MessageResponse("Deleted task successfully"))
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @GetMapping("/count")
    fun taskAnswerCount(): Long {
        return taskAnswerService!!.getTaskAnswerListLength()
    }
    @PostMapping("/filteredlist")
    fun getAnswerExistence(@Valid @RequestBody request: TaskAnswerExistenceCheckRequest): List<TaskAnswer> {
        return taskAnswerService!!.isTaskAnswerItemValid(request.userId!!, request.taskId!!)
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateItem(@PathVariable id: String, @Valid @RequestBody taskAnswer: TaskAnswer): ResponseEntity<*> {
        return if (taskAnswerService!!.isTaskAnswerItemValid(id)) {
            var updatedId = taskAnswerService!!.updateTaskAnswerItem(id, taskAnswer)

            if (updatedId.isNotEmpty()){
                ResponseEntity.ok<Any>(
                    MessageResponse("Updated task answer successfully")
                )
            } else {
                ResponseEntity.badRequest().body<Any>(
                    MessageResponse("Failed task answer update")
                )
            }
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }
}