package icu.ikakosickij.results.backend.controller

import icu.ikakosickij.results.backend.model.ERole
import icu.ikakosickij.results.backend.model.Role
import icu.ikakosickij.results.backend.model.User
import icu.ikakosickij.results.backend.payload.request.LoginRequest
import icu.ikakosickij.results.backend.payload.request.SignupRequest
import icu.ikakosickij.results.backend.payload.response.JwtResponse
import icu.ikakosickij.results.backend.payload.response.MessageResponse
import icu.ikakosickij.results.backend.repository.RoleRepository
import icu.ikakosickij.results.backend.repository.UserRepository
import icu.ikakosickij.results.backend.security.jwt.JwtUtils
import icu.ikakosickij.results.backend.security.services.UserDetailsImpl

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer
import java.util.function.Supplier
import java.util.stream.Collectors;

import javax.validation.Valid;

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null
    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
        if (userRepository!!.existsByUsername(signUpRequest.username)!!) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository!!.existsByEmail(signUpRequest.email)!!) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = User(
            signUpRequest.username,
            signUpRequest.email,
            encoder!!.encode(signUpRequest.password)
        )
        val strRoles: Set<String>? = signUpRequest.roles
        val roles: MutableSet<Role> = HashSet<Role>()
        if (strRoles == null) {
            val userRole: Role? = roleRepository!!.findByName(ERole.ROLE_USER)
                ?.orElseThrow {
                    RuntimeException(
                        "Error: Role is not found."
                    )
                }
            if (userRole != null) {
                roles.add(userRole)
            }
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role? = roleRepository!!.findByName(ERole.ROLE_ADMIN)
                            ?.orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        if (adminRole != null) {
                            roles.add(adminRole)
                        }
                    }
                    else -> {
                        val userRole: Role? = roleRepository!!.findByName(ERole.ROLE_USER)
                            ?.orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        if (userRole != null) {
                            roles.add(userRole)
                        }
                    }
                }
            })
        }
        user.roles = roles
        userRepository!!.save(user)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}