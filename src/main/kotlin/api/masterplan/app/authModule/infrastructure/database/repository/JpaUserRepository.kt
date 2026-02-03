package api.masterplan.app.authModule.infrastructure.database.repository

import api.masterplan.app.authModule.infrastructure.database.entity.AppUserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaUserRepository: CrudRepository<AppUserEntity, Long> {

    fun findByLogin(login: String): AppUserEntity?

    fun findByUid(uid: UUID): AppUserEntity?

    fun existsByLogin(login: String): Boolean

}