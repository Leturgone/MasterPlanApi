package api.masterplan.app.userManagementModule.infrastructure.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "role")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "title", nullable = false, unique = true)
    val  title: String,

    @ManyToMany(mappedBy = "roles")
    val usersWithRole: Set<AppUserEntity> = hashSetOf()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as RoleEntity
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode() ?: 0
    }

    override fun toString(): String {
        return "RoleEntity(id=$id, title='$title')"
    }
}
