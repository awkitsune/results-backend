package icu.ikakosickij.results.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.util.Date
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class TaskAnswer {
    @Id
    var id: String? = null

    @NotBlank
    @DBRef
    var task: Task? = null

    @NotBlank
    @DBRef
    var user: User? = null

    @NotBlank
    var file: String? = null

    @Size(max = 1024)
    var commentary: String? = null

    @NotBlank
    var mark: Double? = -1.0

    @NotBlank
    var dateTime: Date? = null

    constructor() {}
    constructor(task: Task?, user: User?, file: String?, commentary: String?, mark: Double?) {
        this.task = task
        this.user = user
        this.file = file
        this.commentary = commentary
        this.mark = mark
    }
}