package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole

interface UserService {

    fun getUserByLogin(login: UserLogin): AppUserDetails

    fun resetPasswordForUser(userId: UserId, newPassword: UserPassword): UserId

    fun createUser(login: UserLogin,password: UserPassword, roles: Set<UserRole>, employeeInfo: EmployeeInfo): UserId

    fun getUser(userId: UserId): AppUserDetails

    fun deleteUser(userId: UserId): UserId
}