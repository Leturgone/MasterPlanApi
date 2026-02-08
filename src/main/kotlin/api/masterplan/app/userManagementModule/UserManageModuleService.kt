package api.masterplan.app.userManagementModule

interface UserManageModuleService {

    fun getUserDetailsByUsername(username: String): Result<UserCredentialsDto>

    fun validateCredentials(login: String,password: String): Result<UserCredentialsDto>

}