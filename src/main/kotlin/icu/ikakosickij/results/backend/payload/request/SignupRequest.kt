package icu.ikakosickij.results.backend.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    var username: String? = null

    @NotBlank
    @Size(max = 320)
    @Email
    var email: String? = null
    var roles: Set<String>? = null
        private set

    @NotBlank
    @Size(min = 6, max = 40)
    var password: String? = null

    fun setRole(roles: Set<String>?) {
        this.roles = roles
    }
}