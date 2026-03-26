package api.masterplan.app.userManagementModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.userManagement.UserCredentialsDto
import api.masterplan.app.apiContracts.userManagement.UserManageModuleService
import api.masterplan.app.userManagementModule.application.command.GetUserByLoginCommand
import api.masterplan.app.userManagementModule.application.command.ValidateCredentialsCommand
import api.masterplan.app.userManagementModule.application.usecase.GetUserByLoginUseCase
import api.masterplan.app.userManagementModule.application.usecase.ValidateCredentialsUseCase
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import org.springframework.stereotype.Service

@Service
class UserManageModuleServiceImpl(
    private val getUserByLoginUseCase: GetUserByLoginUseCase,
    private val validateCredentialsUseCase: ValidateCredentialsUseCase
): UserManageModuleService {

    override fun getUserDetailsByUsername(username: String): Result<UserCredentialsDto> {
        return try {
            val command = GetUserByLoginCommand(UserLogin.validate(username))
            val result = getUserByLoginUseCase(command).getOrThrow()
            return Result.success(InterModuleUserToDtoSuccessMapper.toDto(result))
        }catch (e: UserManagementException) {
            val exception = InterModuleUserToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun validateCredentials(login: String, password: String): Result<UserCredentialsDto> {
        return try {
            val command = ValidateCredentialsCommand(UserLogin.validate(login), UserPassword.validate(password))
            val result = validateCredentialsUseCase(command).getOrThrow()
            return Result.success(InterModuleUserToDtoSuccessMapper.toDto(result))
        }catch (e: UserManagementException){
            val exception = InterModuleUserToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

}