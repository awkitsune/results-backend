package icu.ikakosickij.results.backend.model

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
class User {
    @Id
    var id: String? = null

    @NotBlank
    @Size(max = 20)
    var username: String? = null

    @NotBlank
    @Size(max = 320)
    @Email
    var email: String? = null

    @NotBlank
    @Size(max = 120)
    var password: String? = null

    @DBRef
    var roles: Set<Role> = HashSet()

    constructor() {}
    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }
}