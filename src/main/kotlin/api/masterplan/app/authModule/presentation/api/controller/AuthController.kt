package api.masterplan.app.authModule.presentation.api.controller

import api.masterplan.app.authModule.application.command.LoginCommand
import api.masterplan.app.authModule.application.usecase.LoginUseCase
import api.masterplan.app.authModule.presentation.dto.LoginRequest
import api.masterplan.app.authModule.presentation.dto.LoginResponse
import api.masterplan.app.authModule.presentation.mapper.AuthExceptionToHttpCodeMapper
import api.masterplan.app.authModule.presentation.mapper.RequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Аутентификация")
class AuthController(
    private val loginUseCase: LoginUseCase
) {

    @Operation(
        summary = "Login",
        description = "Аутентификация с возвращением JWT токена",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = LoginResponse.Success::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные (пустой логин/пароль)",
                content = [Content(schema = Schema(implementation = LoginResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Неверный логин или пароль",
                content = [Content(schema = Schema(implementation = LoginResponse.Error::class))]
            )
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{

        val loginCommand = try {
            val userPassword = RequestToDomainMapper.toDomainPassword(loginRequest.password)
            val userLogin = RequestToDomainMapper.toDomainLogin(loginRequest.login)
            LoginCommand(
                login = userLogin,
                password = userPassword
            )
        }catch (e: Exception){
            val status = AuthExceptionToHttpCodeMapper.exceptionToHttpCode(e)
            val body = LoginResponse.Error(
                status = status.value(),e.message, LocalDateTime.now()
            )
            return ResponseEntity.status(status).body(body)
        }


        return  loginUseCase(loginCommand).fold(
            onSuccess = { token ->
                ResponseEntity.ok(LoginResponse.Success(
                    token = token.token
                ))
            },
            onFailure = {error ->
                val status = AuthExceptionToHttpCodeMapper.exceptionToHttpCode(error)
                val body = LoginResponse.Error(
                    status = status.value(),error.message, LocalDateTime.now()
                )
                ResponseEntity.status(status).body(body)
            }
        )
    }
}