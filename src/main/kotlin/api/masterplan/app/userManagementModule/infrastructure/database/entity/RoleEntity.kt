package api.masterplan.app.userManagementModule.infrastructure.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,

    @Column(name = "title", nullable = false, unique = true)
    val  title: String,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "app_user_has_role",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "app_user_id", referencedColumnName = "id")]
    )
    val usersWithRole: HashSet<AppUserEntity> = hashSetOf()
)
