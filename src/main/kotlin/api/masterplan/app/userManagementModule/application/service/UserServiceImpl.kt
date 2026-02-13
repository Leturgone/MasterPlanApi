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
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(rollbackFor = [Exception::class])
    override fun getUserByLogin(login: UserLogin): AppUserDetails {
//        return try {
//            val user = userRepository.findByLogin(login) ?: throw UserManagementException.UserNotFoundException(login)
//            val appUserDetails = AppUserDetails(
//                id = user.id,
//                login = login,
//                password = user.password,
//                roles = user.roles
//            )
//            logger.info("User with login=${login.value} found")
//            Result.success(appUserDetails)
//        }catch (e: Exception){
//            logger.warn("User with login =${login.value} not found", e)
//            Result.failure(e)
//        }
        val user = userRepository.findByLogin(login) ?: throw UserManagementException.UserNotFoundException(login)
        val appUserDetails = AppUserDetails(
            id = user.id,
            login = login,
            password = user.password,
            roles = user.roles
        )
        return appUserDetails
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun resetPasswordForUser(userId: UserId, newPassword: UserPassword): UserId {
//        return try {
//            val editedUserId = userRepository.setPassword(userId,newPassword) ?: throw UserManagementException.FailedToResetPasswordForUser(userId)
//            logger.info("User with id=${userId.value} updated")
//            Result.success(editedUserId)
//        }catch (e: Exception){
//            logger.warn("Failed to update user with id =${userId.value}", e)
//            Result.failure(e)
//        }
        val editedUserId = userRepository.setPassword(userId,newPassword) ?: throw UserManagementException.FailedToResetPasswordForUser(userId)
        return editedUserId
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createUser(login: UserLogin, password: UserPassword, roles: Set<UserRole>): UserId {
//        return try {
//            if (userRepository.isUserExist(login)) return Result.failure(UserManagementException.UserAlreadyExistsException(login))
//            val newUser = AppUser.create(login = login, rawPassword = password, roles = roles)
//            val userId = userRepository.saveUser(newUser) ?: throw UserManagementException.FailedToCreateUserException(login)
//            logger.info("User with id=${userId.value} saved")
//            Result.success(userId)
//        }catch (e: Exception){
//            logger.warn("Failed to save user with login =${login.value}", e)
//            Result.failure(e)
//        }
        if (userRepository.isUserExist(login)) throw UserManagementException.UserAlreadyExistsException(login)
        val newUser = AppUser.create(login = login, rawPassword = password, roles = roles)
        val userId = userRepository.saveUser(newUser) ?: throw UserManagementException.FailedToCreateUserException(login)
        return userId
    }

    override fun getUser(userId: UserId): AppUserDetails {
//        return try {
//
//            logger.info("Get user with id=${userId.value} ")
//            Result.success(userDetails)
//        }catch (e: Exception){
//            logger.warn("Failed to get user with id =${userId.value}", e)
//            Result.failure(e)
//        }
        val user = userRepository.getUser(userId) ?: throw UserManagementException.UserNotExistsException(userId)
        val userDetails = AppUserDetails(
            id = user.id,
            login = user.login,
            password = user.password,
            roles = user.roles
        )
        return userDetails
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteUser(userId: UserId): UserId {
//        return try {
//
//            logger.info("Delete user with id=${userId.value} ")
//            Result.success(deletedUserId)
//        }catch (e: Exception){
//            logger.warn("Failed to delete user with id =${userId.value}", e)
//            Result.failure(e)
//        }
        val deletedUserId = userRepository.deleteUser(userId) ?: throw UserManagementException.FailedToDeleteUserException(userId)
        return deletedUserId
    }


}