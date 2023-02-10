package icu.ikakosickij.results.backend.repository

import icu.ikakosickij.results.backend.model.Task
import icu.ikakosickij.results.backend.model.TaskAnswer
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskAnswerRepository : MongoRepository<TaskAnswer, String>