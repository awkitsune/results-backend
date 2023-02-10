package icu.ikakosickij.results.backend.model

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class TaskAnswer {
    @Id
    var id: String? = null

    @NotBlank
    var task: Task? = null

    @NotBlank
    var user: User? = null

    @NotBlank
    var file: String? = null

    @Size(max = 1024)
    var commentary: String? = null

    constructor() {}
    constructor(task: Task?, user: User?, file: String?, commentary: String?) {
        this.task = task
        this.user = user
        this.file = file
        this.commentary = commentary
    }
}