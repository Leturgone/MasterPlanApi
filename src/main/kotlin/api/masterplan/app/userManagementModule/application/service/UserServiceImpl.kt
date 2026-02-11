package api.masterplan.app.userManagementModule.application.service

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.UserRepository
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun getUserByLogin(login: UserLogin): Result<AppUserDetails> {
        return try {
            val user = userRepository.findByLogin(login) ?: throw UserManagementException.UserNotFoundException(login)
            val appUserDetails = AppUserDetails(
                id = user.id,
                login = login,
                password = user.password,
                roles = user.roles
            )
            logger.info("User with login=${login.value} found")
            Result.success(appUserDetails)
        }catch (e: Exception){
            logger.warn("User with login =${login.value} not found", e)
            Result.failure(e)
        }
    }

    override fun resetPasswordForUser(userId: UserId, newPassword: UserPassword): Result<UserId> {
        return try {

            val editedUserId = userRepository.setPassword(userId,newPassword) ?: throw UserManagementException.FailedToResetPasswordForUser(userId)
            logger.info("User with id=${userId.value} updated")
            Result.success(editedUserId)
        }catch (e: Exception){
            logger.warn("Failed to update user with id =${userId.value}", e)
            Result.failure(e)
        }
    }

    override fun createUser(login: UserLogin, password: UserPassword, roles: Set<UserRole>): Result<UserId> {
        return try {
            val newUser = AppUser.create(login = login, rawPassword = password, roles = roles)
            val userId = userRepository.saveUser(newUser) ?: throw UserManagementException.FailedToCreateUserException(login)
            logger.info("User with id=${userId.value} saved")
            Result.success(userId)
        }catch (e: Exception){
            logger.warn("Failed to save user with login =${login.value}", e)
            Result.failure(e)
        }
    }

    override fun getUser(userId: UserId): Result<AppUserDetails> {
        return try {
            val user = userRepository.getUser(userId) ?: throw UserManagementException.UserNotExistsException(userId)
            val userDetails = AppUserDetails(
                id = user.id,
                login = user.login,
                password = user.password,
                roles = user.roles
            )
            logger.info("Get user with id=${userId.value} ")
            Result.success(userDetails)
        }catch (e: Exception){
            logger.warn("Failed to get user with id =${userId.value}", e)
            Result.failure(e)
        }
    }

    override fun deleteUser(userId: UserId): Result<UserId> {
        return try {
            val deletedUserId = userRepository.deleteUser(userId) ?: throw UserManagementException.FailedToDeleteUserException(userId)
            logger.info("Delete user with id=${userId.value} ")
            Result.success(deletedUserId)
        }catch (e: Exception){
            logger.warn("Failed to delete user with id =${userId.value}", e)
            Result.failure(e)
        }

    }


}