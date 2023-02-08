package icu.ikakosickij.results.backend.repository

import icu.ikakosickij.results.backend.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface UserRepository : MongoRepository<User?, String?> {
    fun findByUsername(username: String?): Optional<User?>?
    fun existsByUsername(username: String?): Boolean?
    fun existsByEmail(email: String?): Boolean?
}
