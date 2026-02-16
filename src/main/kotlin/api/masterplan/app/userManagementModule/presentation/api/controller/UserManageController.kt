package api.masterplan.app.userManagementModule.presentation.api.controller

import api.masterplan.app.userManagementModule.application.command.*
import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.usecase.*
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.presentation.dto.request.CreateProfileRequest
import api.masterplan.app.userManagementModule.presentation.dto.request.ResetPasswordRequest
import api.masterplan.app.userManagementModule.presentation.dto.request.ValidateCredentialsRequest
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserDataResponse
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserUidResponse
import api.masterplan.app.userManagementModule.presentation.mapper.UserDomainToResponseMapper
import api.masterplan.app.userManagementModule.presentation.mapper.UserExceptionToHttpCodeMapper
import api.masterplan.app.userManagementModule.presentation.mapper.UserRequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/api/v1/admin/users")
@Tag(name = "Users", description = "Управление пользователями")
class UserManageController(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserByLoginUseCase: GetUserByLoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val validateCredentialsUseCase: ValidateCredentialsUseCase
) {

    @Operation(
        summary = "Создание профиля",
        description = "Создание профиля с передачей учётных данных и данных сотрудника",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль успешно создан",
                content = [Content(schema = Schema(implementation = UserUidResponse.Success::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: пустой логин/пароль, некорректные роли или неверный формат ID руководителя",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Пользователь с указанным логином уже существует",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании пользователя",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @PostMapping("/createProfile")
    fun createUser(@RequestBody request: CreateProfileRequest): ResponseEntity<UserUidResponse>{
        val employeeInfo = EmployeeInfo(
            name = request.name,
            surname = request.surname,
            patronymic = request.patronymic,
            directorId = request.directorId,
        )

        val command = try {
            val loginValidated = UserRequestToDomainMapper.loginToDomain(request.login)
            val passwordValidated = UserRequestToDomainMapper.passwordToDomain(request.password)
            val rolesValidated = UserRequestToDomainMapper.rolesToDomain(request.roles)
            CreateUserCommand(
                login = loginValidated,
                password = passwordValidated,
                roles = rolesValidated,
                employeeInfo = employeeInfo,
            )
        }catch (e: UserManagementException){
            return handleExceptionUserId(e)
        }

        return createUserUseCase(command).handleUserIdResult()
    }

    @Operation(
        summary = "Удаление пользователя по ID",
        description = "Удаляет пользователя из системы по указанному UUID",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь успешно удалён. Возвращается идентификатор удалённого аккаунта",
                content = [Content(schema = Schema(implementation = UserUidResponse.Success::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат ID пользователя (не UUID)",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Удаляемый пользователь не найден",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable(value = "id")id: UUID): ResponseEntity<UserUidResponse> {

        val command = try {
            DeleteUserCommand(UserRequestToDomainMapper.idToDomain(id))
        }catch (e: UserManagementException.InvalidUserCredentialsException){
            return handleExceptionUserId(e)
        }

        return deleteUserUseCase(command).handleUserIdResult()

    }

    @Operation(
        summary = "Получение данных пользователя по логину",
        description = "Возвращает полную информацию о пользователе на основе указанного логина.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь найден. Возвращаются данные профиля",
                content = [Content(schema = Schema(implementation = UserDataResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат логина",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь с указанным логином не найден",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @GetMapping("/getUserByLogin/{login}")
    fun getUserByLogin(@PathVariable(value = "login") login: String): ResponseEntity<UserDataResponse> {
        val command = try {
            val loginValidated = UserRequestToDomainMapper.loginToDomain(login)
            GetUserByLoginCommand(loginValidated)
        }catch (e: UserManagementException.InvalidUserCredentialsException){
            return handleExceptionUserData(e)
        }

        return getUserByLoginUseCase(command).handleUserResult()
    }

    @Operation(
        summary = "Получение данных пользователя по id",
        description = "Возвращает полную информацию о пользователе по id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь найден. Возвращаются данные профиля",
                content = [Content(schema = Schema(implementation = UserDataResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь с указанным id не найден",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @GetMapping("/getUserById/{id}")
    fun getUserById(@PathVariable(value = "id") id: UUID): ResponseEntity<UserDataResponse> {
        val command = try {
            val idValidated = UserRequestToDomainMapper.idToDomain(id)
            GetUserByIdCommand(idValidated)
        }catch (e: UserManagementException.InvalidUserCredentialsException){
            return handleExceptionUserData(e)
        }

        return getUserByIdUseCase(command).handleUserResult()
    }

    @Operation(
        summary = "Сброс пароля пользователя по id",
        description = "Заменяет пароль пользователя по id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Пароль сброшен. Возвращаются id пользователя",
                content = [Content(schema = Schema(implementation = UserUidResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь с указанным id не найден",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат пароля",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserUidResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @PatchMapping("/resetPassword/")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<UserUidResponse>{
        val id = UserId(request.userId)
        val command = try {
            val passwordValidated = UserRequestToDomainMapper.passwordToDomain(request.password)
            ResetPasswordCommand(id,passwordValidated)
        }catch (e: UserManagementException.InvalidUserCredentialsException){
            return handleExceptionUserId(e)
        }

        return resetPasswordUseCase(command).handleUserIdResult()
    }


    @Operation(
        summary = "Валидация учетных данных",
        description = "Проверяет правильность учетных данных",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Данные валидированы",
                content = [Content(schema = Schema(implementation = UserDataResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь с указанным login не найден",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат логина или пароля",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserDataResponse.Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @PostMapping("/validateCredentials/")
    fun validateCredentials(@RequestBody credentials: ValidateCredentialsRequest):ResponseEntity<UserDataResponse>{
        val command = try {
            val loginValidated = UserRequestToDomainMapper.loginToDomain(credentials.login)
            val passwordValidated = UserRequestToDomainMapper.passwordToDomain(credentials.password)
            ValidateCredentialsCommand(loginValidated,passwordValidated)
        }catch (e: UserManagementException.InvalidUserCredentialsException){
            return handleExceptionUserData(e)
        }

        return validateCredentialsUseCase(command).handleUserResult()
    }



    private fun Result<AppUserDetails>.handleUserResult(): ResponseEntity<UserDataResponse> = this.fold(
            onSuccess = { user ->
                val resp = UserDomainToResponseMapper.userToResponse(user)
                ResponseEntity.ok(resp)
            },
            onFailure = { error ->
                val status = UserExceptionToHttpCodeMapper.exceptionToHttpCode(error)
                val body = UserDataResponse.Error(
                    status = status.value(),
                    message = error.message,
                    timestamp = LocalDateTime.now()
                )
                ResponseEntity.status(status).body(body)
            }
        )


    private fun Result<UserId>.handleUserIdResult(): ResponseEntity<UserUidResponse> = this.fold(
        onSuccess = {uid ->
            ResponseEntity.ok(UserUidResponse.Success(
                uid.value
            ))
        },
        onFailure = {error ->
            val status = UserExceptionToHttpCodeMapper.exceptionToHttpCode(error)
            val body = UserUidResponse.Error(
                status = status.value(),error.message, LocalDateTime.now()
            )
            ResponseEntity.status(status).body(body)
        }
    )


    private fun handleExceptionUserId(e: UserManagementException): ResponseEntity<UserUidResponse> {
        val status = UserExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = UserUidResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }


    private fun handleExceptionUserData(e: UserManagementException): ResponseEntity<UserDataResponse> {
        val status = UserExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = UserDataResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }




}