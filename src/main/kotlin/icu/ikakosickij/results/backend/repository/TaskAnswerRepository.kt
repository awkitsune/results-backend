package icu.ikakosickij.results.backend.repository

import icu.ikakosickij.results.backend.model.TaskAnswer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.Optional

interface TaskAnswerRepository : MongoRepository<TaskAnswer, String> {
    fun findByUserAndTaskId(userId: String, taskId: String): List<TaskAnswer>
}