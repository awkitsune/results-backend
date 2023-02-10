package icu.ikakosickij.results.backend.repository

import icu.ikakosickij.results.backend.model.Task
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskRepository : MongoRepository<Task, String>