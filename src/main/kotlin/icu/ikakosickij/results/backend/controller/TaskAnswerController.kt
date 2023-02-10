package icu.ikakosickij.results.backend.controller

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.model.TaskAnswer
import icu.ikakosickij.results.backend.payload.response.MessageResponse
import icu.ikakosickij.results.backend.security.services.TaskAnswerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    fun taskList(): List<TaskAnswer> {
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
    fun taskCount(): Long {
        return taskAnswerService!!.getTaskAnswerListLength()
    }
}