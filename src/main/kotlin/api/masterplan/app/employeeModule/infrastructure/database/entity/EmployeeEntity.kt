package api.masterplan.app.employeeModule.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "employee")
class EmployeeEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "name", length = 45, nullable = false)
    val name: String,

    @Column(name = "surname", length = 100, nullable = false)
    val surname: String,

    @Column(name = "patronymic", length = 45)
    val patronymic: String? = null,

    @Column(name = "director_id")
    val directorId: UUID? = null,

    @Column(name = "app_user_id",nullable = false, unique = true)
    val appUserId: UUID,

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as EmployeeEntity
        return  id == other.id
    }
    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String{
        return "EmployeeEntity(id=$id, name='$name', surname='$surname',patronymic=${patronymic?:""})"
    }
}