package icu.ikakosickij.results.backend.model

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class Task {
    @Id
    var id: String? = null

    @NotBlank
    @Size(max = 2048)
    var taskContent: String? = null

    constructor() {}
    constructor(taskContent: String?) {
        this.taskContent = taskContent
    }
}