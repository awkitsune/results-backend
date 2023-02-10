package icu.ikakosickij.results.backend.controller

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.payload.response.MessageResponse
import icu.ikakosickij.results.backend.security.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/task")
class TaskController {
    @Autowired
    var taskService: TaskService? = null

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: String): Optional<Task> {
        return taskService!!.getTaskById(id)
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    fun addItemToList(@Valid @RequestBody task: Task): ResponseEntity<*> {
        var id = taskService!!.addItemToTheList(task)

        return if (id.isNotEmpty()){
            ResponseEntity.ok<Any>(
                MessageResponse("Added task successfully"))
        } else {
            ResponseEntity.badRequest().body<Any>(
                MessageResponse("Failed task addition"))
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateItem(@PathVariable id: String, @Valid @RequestBody task: Task): ResponseEntity<*> {
        return if (taskService!!.isTaskItemValid(id)) {
            var updatedId = taskService!!.updateTaskItem(id, task)

            if (updatedId.isNotEmpty()){
                ResponseEntity.ok<Any>(
                    MessageResponse("Added task successfully"))
            } else {
                ResponseEntity.badRequest().body<Any>(
                    MessageResponse("Failed task addition"))
            }
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @GetMapping("/list")
    fun taskList(): List<Task> {
        return taskService!!.getTaskList()
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteItem(@PathVariable id: String): ResponseEntity<*> {
        return if (taskService!!.isTaskItemValid(id)) {
            taskService!!.deleteItem(id)

            ResponseEntity.ok<Any>(
                MessageResponse("Deleted task successfully"))
        } else {
            ResponseEntity.notFound().build<Any>()
        }
    }

    @GetMapping("/count")
    fun taskCount(): Long {
        return taskService!!.getTaskListLength()
    }
}