package api.masterplan.app.userManagementModule.infrastructure.database.repository

import api.masterplan.app.userManagementModule.infrastructure.database.entity.AppUserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaUserRepository: CrudRepository<AppUserEntity, UUID> {

    fun findByLogin(login: String): AppUserEntity?

    fun existsByLogin(login: String): Boolean

}