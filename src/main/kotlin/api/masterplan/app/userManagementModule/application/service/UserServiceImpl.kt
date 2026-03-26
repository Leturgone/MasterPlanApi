package api.masterplan.app.userManagementModule.application.service

import api.masterplan.app.logging.annotations.LoggingMethod
import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.ports.EmployeeCreationPort
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.UserRepository
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val employeeCreationPort: EmployeeCreationPort
): UserService {

    @LoggingMethod(moduleName = "userManagementModule")
    override fun getUserById(userId: UserId): AppUserDetails {
        val user = userRepository.getUser(userId) ?: throw UserManagementException.UserNotExistsException(userId)
        val appUserDetails = AppUserDetails(
            id = user.id,
            login = user.login,
            password = user.password,
            roles = user.roles
        )
        return appUserDetails
    }

    @LoggingMethod(moduleName = "userManagementModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun getUserByLogin(login: UserLogin): AppUserDetails {
        val user = userRepository.findByLogin(login) ?: throw UserManagementException.UserNotFoundException(login)
        val appUserDetails = AppUserDetails(
            id = user.id,
            login = user.login,
            password = user.password,
            roles = user.roles
        )
        return appUserDetails
    }

    @LoggingMethod(moduleName = "userManagementModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun resetPasswordForUser(userId: UserId, newPassword: UserPassword): UserId {
        userRepository.getUser(userId)?: throw UserManagementException.UserNotExistsException(userId)
        val editedUserId = userRepository.setPassword(userId,newPassword) ?: throw UserManagementException.FailedToResetPasswordForUser(userId)
        return editedUserId
    }

    @LoggingMethod(moduleName = "userManagementModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun createUser(login: UserLogin, password: UserPassword,
                            roles: Set<UserRole>,employeeInfo: EmployeeInfo): UserId {
        if (userRepository.isUserExist(login)) throw UserManagementException.UserAlreadyExistsException(login)
        val newUser = AppUser.create(login = login, rawPassword = password, roles = roles)
        val userId = userRepository.saveUser(newUser) ?: throw UserManagementException.FailedToCreateUserException(login)
        employeeCreationPort.createEmployee(userId,employeeInfo)
        return userId
    }


    @LoggingMethod(moduleName = "userManagementModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteUser(userId: UserId): UserId {
        val user = userRepository.getUser(userId)?: throw UserManagementException.UserNotExistsException(userId)
        if (user.isAdmin()) throw UserManagementException.UserCantBeDeleted(userId)
        val deletedUserId = userRepository.deleteUser(userId) ?: throw UserManagementException.FailedToDeleteUserException(userId)
        return deletedUserId
    }


}