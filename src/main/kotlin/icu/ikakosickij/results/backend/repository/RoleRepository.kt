package icu.ikakosickij.results.backend.repository

import icu.ikakosickij.results.backend.model.ERole
import icu.ikakosickij.results.backend.model.Role

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface RoleRepository : MongoRepository<Role?, String?> {
    fun findByName(name: ERole?): Optional<Role?>?
}