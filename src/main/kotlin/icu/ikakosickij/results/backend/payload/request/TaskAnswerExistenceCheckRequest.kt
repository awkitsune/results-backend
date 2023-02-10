package icu.ikakosickij.results.backend.payload.request

import javax.validation.constraints.NotBlank

class TaskAnswerExistenceCheckRequest {
    @NotBlank
    var userId: String? = null

    @NotBlank
    var taskId: String? = null
}