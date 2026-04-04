package api.masterplan.app.userManagementModule.presentation.api.controller

import api.masterplan.app.userManagementModule.application.command.*
import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.usecase.*
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.presentation.api.exceptionHandler.UserControllerExceptionHandler
import api.masterplan.app.userManagementModule.presentation.dto.request.CreateProfileRequest
import api.masterplan.app.userManagementModule.presentation.dto.request.ResetPasswordRequest
import api.masterplan.app.userManagementModule.presentation.dto.request.ValidateCredentialsRequest
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserDataResponse
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserErrorResponse
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserUidResponse
import api.masterplan.app.userManagementModule.presentation.mapper.UserDomainToResponseMapper
import api.masterplan.app.userManagementModule.presentation.mapper.UserRequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@UserControllerExceptionHandler
@RequestMapping("/api/v1/users/admin/")
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
                content = [Content(schema = Schema(implementation = UserUidResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: пустой логин/пароль, некорректные роли или неверный формат ID руководителя",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Пользователь с указанным логином уже существует",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании пользователя",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @PostMapping("/user")
    fun createUser(
        @RequestBody request: CreateProfileRequest
    ): ResponseEntity<UserUidResponse>{
        val employeeInfo = EmployeeInfo(
            name = request.name,
            surname = request.surname,
            patronymic = request.patronymic,
            directorId = request.directorId,
        )
        val loginValidated = UserRequestToDomainMapper.loginToDomain(request.login)
        val passwordValidated = UserRequestToDomainMapper.passwordToDomain(request.password)
        val rolesValidated = UserRequestToDomainMapper.rolesToDomain(request.roles)

        val command = CreateUserCommand(
            login = loginValidated,
            password = passwordValidated,
            roles = rolesValidated,
            employeeInfo = employeeInfo,
        )

        val result = createUserUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.idToResponse(result)

        return ResponseEntity.ok(resp)
    }

    @Operation(
        summary = "Удаление пользователя по ID",
        description = "Удаляет пользователя из системы по указанному UUID",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь успешно удалён. Возвращается идентификатор удалённого аккаунта",
                content = [Content(schema = Schema(implementation = UserUidResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат ID пользователя (не UUID)",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Удаляемый пользователь не найден",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @DeleteMapping("/user/{id}")
    fun deleteUser(
        @PathVariable(value = "id")id: UUID
    ): ResponseEntity<UserUidResponse> {

        val userId = UserRequestToDomainMapper.idToDomain(id)
        val command = DeleteUserCommand(userId)

        val result = deleteUserUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.idToResponse(result)

        return ResponseEntity.ok(resp)

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
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь с указанным логином не найден",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @GetMapping("/user/login/{login}")
    fun getUserByLogin(
        @PathVariable(value = "login") login: String
    ): ResponseEntity<UserDataResponse> {
        val loginValidated = UserRequestToDomainMapper.loginToDomain(login)
        val command = GetUserByLoginCommand(loginValidated)

        val result = getUserByLoginUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.userToResponse(result)

        return ResponseEntity.ok(resp)
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
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @GetMapping("/user/id/{id}")
    fun getUserById(
        @PathVariable(value = "id") id: UUID
    ): ResponseEntity<UserDataResponse> {
        val idValidated = UserRequestToDomainMapper.idToDomain(id)
        val command = GetUserByIdCommand(idValidated)

        val result = getUserByIdUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.userToResponse(result)

        return ResponseEntity.ok(resp)
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
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат пароля",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @PatchMapping("/user/{id}/password")
    fun resetPassword(
        @PathVariable(value = "id") id: UUID,
        @RequestBody request: ResetPasswordRequest
    ): ResponseEntity<UserUidResponse>{
        val id = UserId(id)
        val passwordValidated = UserRequestToDomainMapper.passwordToDomain(request.password)
        val command = ResetPasswordCommand(id,passwordValidated)

        val result = resetPasswordUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.idToResponse(result)

        return ResponseEntity.ok(resp)
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
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректный формат логина или пароля",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера",
                content = [Content(schema = Schema(implementation = UserErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )
        ]
    )
    @PostMapping("/validateCredentials")
    fun validateCredentials(
        @RequestBody credentials: ValidateCredentialsRequest
    ):ResponseEntity<UserDataResponse>{
        val loginValidated = UserRequestToDomainMapper.loginToDomain(credentials.login)
        val passwordValidated = UserRequestToDomainMapper.passwordToDomain(credentials.password)
        val command = ValidateCredentialsCommand(loginValidated,passwordValidated)

        val result = validateCredentialsUseCase(command).getOrThrow()
        val resp = UserDomainToResponseMapper.userToResponse(result)

        return ResponseEntity.ok(resp)
    }


}