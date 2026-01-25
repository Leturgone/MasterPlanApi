package api.masterplan.app.authModule.infrastructure.database.repository

import api.masterplan.app.authModule.infrastructure.database.entity.AppUserEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaUserRepository: CrudRepository<AppUserEntity, Long> {

    fun findByLogin(login: String): AppUserEntity?

    fun findByUid(uid: UUID): AppUserEntity?

    fun existsByLogin(login: String): Boolean

}