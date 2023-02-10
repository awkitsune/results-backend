package icu.ikakosickij.results.backend.security.services

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.model.TaskAnswer
import icu.ikakosickij.results.backend.repository.TaskAnswerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.Exception

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

    fun isTaskAnswerItemValid(userId: String, taskId: String): Boolean {
        return taskAnswerRepository!!.findByUserAndTaskId(userId, taskId).isNotEmpty()
    }
    fun getTaskAnswerListLength(): Long {
        return taskAnswerRepository!!.count()
    }
}