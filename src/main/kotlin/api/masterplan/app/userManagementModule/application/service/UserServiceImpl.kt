package api.masterplan.app.userManagementModule.application.service

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.UserRepository
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
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

    override fun editUser(userId: UserId, newUserData: AppUserDetails): Result<UserId> {
        TODO("Not yet implemented")
    }

    override fun createUser(login: UserLogin, password: UserPassword, roles: Set<UserRole>): Result<UserId> {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: UserId): Result<AppUserDetails> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: UserId): Result<UserId> {
        TODO("Not yet implemented")
    }


}