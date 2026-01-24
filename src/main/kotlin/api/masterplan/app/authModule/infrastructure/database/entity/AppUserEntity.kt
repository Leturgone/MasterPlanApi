package api.masterplan.app.authModule.infrastructure.database.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_user")
data class AppUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,

    @Column(name = "uid", nullable = false, unique = true)
    val uid: UUID = UUID.randomUUID(),

    @Column(name = "login", nullable = false, unique = true)
    val  login: String,

    @Column(name = "password_hash", nullable = false)
    val passwordHash: String,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "app_user_has_role",
        joinColumns = [JoinColumn(name = "app_user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
        )
    val roles: MutableSet<RoleEntity> = hashSetOf()
)
