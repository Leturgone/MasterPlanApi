package api.masterplan.app.authModule.presentation.api.controller

import api.masterplan.app.authModule.application.command.LoginCommand
import api.masterplan.app.authModule.application.usecase.LoginUseCase
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanAuthException
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanDatabaseException
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanPasswordHashException
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import api.masterplan.app.authModule.presentation.dto.ErrorResponse
import api.masterplan.app.authModule.presentation.dto.LoginRequest
import api.masterplan.app.authModule.presentation.dto.LoginResponse
import api.masterplan.app.authModule.presentation.mapper.ExceptionToHttpCodeMapper
import api.masterplan.app.authModule.presentation.mapper.RequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authtorization")
class AuthController(
    private val loginUseCase: LoginUseCase
) {


    @PostMapping("/login")
    suspend fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{

        val userPassword = RequestToDomainMapper.toDomainPassword(loginRequest.password)
        val userLogin = RequestToDomainMapper.toDomainLogin(loginRequest.login)
        val loginCommand = LoginCommand(
            login = userLogin,
            password = userPassword
        )
        return  loginUseCase(loginCommand).fold(
            onSuccess = { token ->
                ResponseEntity.ok(LoginResponse.Success(
                    token = token.token
                ))
            },
            onFailure = {error ->
                val status = ExceptionToHttpCodeMapper.exceptionToHttpCode(error)
                val body = LoginResponse.Error(
                    status = status.value(),error.message, LocalDateTime.now()
                )
                ResponseEntity.status(status).body(body)
            }
        )
    }
}