package icu.ikakosickij.results.backend.security.services

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.Exception

@Service
class TaskService {
    @Autowired
    var taskRepository: TaskRepository? = null

    fun getTaskList(): List<Task> {
        val taskList: MutableList<Task> = ArrayList()
        taskRepository!!.findAll().forEach{
            if (it != null) {
                taskList.add(it)
            }
        }
        return taskList
    }

    fun getTaskById(id: String): Optional<Task> {
        return taskRepository!!.findById(id)
    }

    fun addItemToTheList(task: Task): String {
        var id = ""
        taskRepository!!.save(task)
        id = task.id!!

        return id
    }

    fun deleteItem(id: String) {
        taskRepository!!.deleteById(id)
    }

    fun updateTaskItem(id: String, task: Task): String {
        var updatedTaskId = ""
        try {
            val updatedTask = taskRepository!!.findById(id).get()
            updatedTask.taskContent = task.taskContent
            taskRepository!!.save(updatedTask)
            updatedTaskId = updatedTask.id!!

            return updatedTaskId
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return updatedTaskId
    }

    fun isTaskItemValid(id: String): Boolean {
        return taskRepository!!.existsById(id)
    }

    fun getTaskListLength(): Long {
        return taskRepository!!.count()
    }
}