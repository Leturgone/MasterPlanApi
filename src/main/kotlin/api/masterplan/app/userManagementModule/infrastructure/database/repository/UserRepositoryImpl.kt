package api.masterplan.app.userManagementModule.infrastructure.database.repository

import api.masterplan.app.logging.LoggingDatabaseMethod
import api.masterplan.app.userManagementModule.domain.interfaces.UserRepository
import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.infrastructure.database.mapper.UserDatabaseEntityMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val  jpaUserRepository: JpaUserRepository,
    private val jpaRoleRepository: JpaRoleRepository
): UserRepository {


    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun findByLogin(login: UserLogin): AppUser? {
        val userEntity = jpaUserRepository.findByLogin(login.value)?: return null
        val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
        return domainUser
    }

    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun isUserExist(login: UserLogin): Boolean {
        return jpaUserRepository.existsByLogin(login.value)
    }


    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun setPassword(userId: UserId, newPassword: UserPassword): UserId? {
        val userEntity = jpaUserRepository.findById(userId.value).getOrNull() ?: return null
        val userWithNewPasswordEntity = userEntity.copy(passwordHash = newPassword.value)
        return UserId(jpaUserRepository.save(userWithNewPasswordEntity).id)
    }


    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun saveUser(user: AppUser): UserId? {
        val roles = jpaRoleRepository.findAll().toSet()
        val databaseEntity = UserDatabaseEntityMapper.toEntity(user,roles)
        val userid = UserId(jpaUserRepository.save(databaseEntity).id)
        return userid
    }

    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun getUser(userId: UserId): AppUser? {
        val userEntity = jpaUserRepository.findById(userId.value).getOrNull() ?: return null
        val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
        return domainUser
    }


    @LoggingDatabaseMethod(moduleName = "userManagementModule")
    override fun deleteUser(userId: UserId): UserId {
        jpaUserRepository.deleteById(userId.value)
        return userId
    }
}