package api.masterplan.app.authModule.infrastructure.database.repository

import api.masterplan.app.authModule.domain.`interface`.UserRepository
import api.masterplan.app.authModule.domain.model.entity.AppUser
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.infrastructure.database.mapper.UserDatabaseEntityMapper
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanDatabaseException
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val  jpaUserRepository: JpaUserRepository
): UserRepository {

    override suspend fun findById(id: UserId): AppUser? {
        val userEntity = jpaUserRepository.findByUid(id.value) ?: return null
        val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
        return domainUser
    }

    override suspend fun findByLogin(login: UserLogin): AppUser? {
        val userEntity = jpaUserRepository.findByLogin(login.value)?: return null
        val domainUser = UserDatabaseEntityMapper.toDomain(userEntity)
        return domainUser
    }

    override suspend fun isUserExist(login: UserLogin): Boolean {
        return jpaUserRepository.existsByLogin(login.value)
    }
}