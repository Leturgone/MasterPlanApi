package api.masterplan.app.authModule.infrastructure.database.repository

import api.masterplan.app.authModule.infrastructure.database.entity.AppUserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*


interface JpaUserRepository: CoroutineCrudRepository<AppUserEntity, Long> {

    suspend fun findByLogin(login: String): AppUserEntity?

    suspend fun findByUid(uid: UUID): AppUserEntity?

    suspend fun existsByLogin(login: String): Boolean

}