package icu.ikakosickij.results.backend.payload.response

class JwtResponse(
    var accessToken: String,
    var id: String,
    var username: String,
    var email: String,
    val roles: List<String>
) {
    var tokenType = "Bearer"

}