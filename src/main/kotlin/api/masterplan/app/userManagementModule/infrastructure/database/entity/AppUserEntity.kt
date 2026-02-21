package api.masterplan.app.userManagementModule.infrastructure.database.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_user")
data class AppUserEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "login", nullable = false, unique = true)
    val  login: String,

    @Column(name = "password_hash", nullable = false)
    val passwordHash: String,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "app_user_has_role",
        joinColumns = [JoinColumn(name = "app_user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
        )
    val roles: Set<RoleEntity> = hashSetOf()

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AppUserEntity
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "AppUserEntity(id=$id, login='$login')"
    }
}
