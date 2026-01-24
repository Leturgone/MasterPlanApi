package api.masterplan.app.authModule.infrastructure.database.repository

import api.masterplan.app.authModule.domain.`interface`.UserRepository
import api.masterplan.app.authModule.domain.model.entity.AppUser
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.infrastructure.database.mapper.UserDatabaseEntityMapper
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanDatabaseException

class UserRepositoryImpl(
    private val  jpaUserRepository: JpaUserRepository
): UserRepository {

    override suspend fun findById(id: UserId): Result<AppUser> {
        val userEntity = jpaUserRepository.findByUid(id.value)?: return Result.failure(
            MasterPlanDatabaseException.UserNotExistsWithId(id)
        )
        return try {
            val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
            Result.success(domainUser)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun findByLogin(login: UserLogin): Result<AppUser> {
        val userEntity = jpaUserRepository.findByLogin(login.value)?: return Result.failure(
            MasterPlanDatabaseException.UserNotExistsWithLogin(login)
        )
        return try {
            val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
            Result.success(domainUser)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun isUserExist(login: UserLogin): Boolean {
        return jpaUserRepository.existsByLogin(login.value)
    }
}