package api.masterplan.app.unitTests

import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.ports.EmployeeCreationPort
import api.masterplan.app.userManagementModule.application.service.UserServiceImpl
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.UserRepository
import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceUnitTest {

    // Данные для моков
    private val userId = UserId.generate()
    private val login = UserLogin("testuser")
    private val password = UserPassword("oldpassword")
    private val newPassword = UserPassword("newpassword")
    private val roles = setOf(UserRole.EMPLOYEE)
    private val adminRoles = setOf(UserRole.ADMIN)
    private val employeeInfo = mockk<EmployeeInfo>()


    private val userRepository = mockk<UserRepository>()
    private val employeeCreationPort = mockk<EmployeeCreationPort>()
    private val userService = UserServiceImpl(userRepository, employeeCreationPort)

    @Test
    fun `getUserById return user details when user exists`() {
        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )
        val expectedDetails = AppUserDetails(
            id = user.id,
            login = user.login,
            password = user.password,
            roles = user.roles
        )

        every { userRepository.getUser(userId) } returns user
        val result = userService.getUserById(userId)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `getUserById throw UserNotExistsException when user does not exist`() {
        every { userRepository.getUser(userId) } returns null
        assertThrows<UserManagementException.UserNotExistsException> {
            userService.getUserById(userId)
        }
    }

    @Test
    fun `getUserByLogin return user details when user exists`() {
        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )
        val expectedDetails = AppUserDetails(
            id = user.id,
            login = user.login,
            password = user.password,
            roles = user.roles
        )

        every { userRepository.findByLogin(login) } returns user
        val result = userService.getUserByLogin(login)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `getUserByLogin throw UserNotFoundException when user does not exist`() {
        every { userRepository.findByLogin(login) } returns null
        assertThrows<UserManagementException.UserNotFoundException> {
            userService.getUserByLogin(login)
        }
    }

    @Test
    fun `resetPasswordForUser reset password successfully`() {
        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )

        every { userRepository.getUser(userId) } returns user
        every { userRepository.setPassword(userId, newPassword) } returns userId

        val result = userService.resetPasswordForUser(userId, newPassword)

        assertEquals(userId, result)
        verify(exactly = 1) { userRepository.setPassword(userId, newPassword) }
    }

    @Test
    fun `resetPasswordForUser throw UserNotExistsException when user does not exist`() {
        every { userRepository.getUser(userId) } returns null
        assertThrows<UserManagementException.UserNotExistsException> {
            userService.resetPasswordForUser(userId, newPassword)
        }
    }

    @Test
    fun `resetPasswordForUser throw FailedToResetPasswordForUser when failed to reset password`() {
        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )

        every { userRepository.getUser(userId) } returns user
        every { userRepository.setPassword(userId, newPassword) } returns null

        assertThrows<UserManagementException.FailedToResetPasswordForUser> {
            userService.resetPasswordForUser(userId, newPassword)
        }
    }

    @Test
    fun `createUser create new user successfully`() {

        every { userRepository.isUserExist(login) } returns false
        every { userRepository.saveUser(any()) } returns userId
        every { employeeCreationPort.createEmployee(userId, employeeInfo) } returns Unit

        val result = userService.createUser(login, password, roles, employeeInfo)

        assertEquals(userId, result)
    }

    @Test
    fun `createUser throw UserAlreadyExistsException when user already exists`() {
        every { userRepository.isUserExist(login) } returns true

        assertThrows<UserManagementException.UserAlreadyExistsException> {
            userService.createUser(login, password, roles, employeeInfo)
        }
    }

    @Test
    fun `createUser throw FailedToCreateUserException when failed to create user`() {

        every { userRepository.isUserExist(login) } returns false
        every { userRepository.saveUser(any()) } returns null

        assertThrows<UserManagementException.FailedToCreateUserException> {
            userService.createUser(login, password, roles, employeeInfo)
        }

    }

    @Test
    fun `deleteUser delete user successfully`() {
        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )

        every { userRepository.getUser(userId) } returns user
        every { userRepository.deleteUser(userId) } returns userId

        val result = userService.deleteUser(userId)

        assertEquals(userId, result)
    }

    @Test
    fun `deleteUser throw UserCantBeDeleted when user is admin`() {
        val adminUser = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = adminRoles
        )

        every { userRepository.getUser(userId) } returns adminUser

        assertThrows<UserManagementException.UserCantBeDeleted> {
            userService.deleteUser(userId)
        }

        verify(exactly = 0) { userRepository.deleteUser(userId) }
    }

    @Test
    fun `deleteUser throw UserNotExistsException when user does not exist`() {

        every { userRepository.getUser(userId) } returns null

        assertThrows<UserManagementException.UserNotExistsException> {
            userService.deleteUser(userId)
        }
    }

    @Test
    fun `deleteUser throw FailedToDeleteUserException when failed to delete user`() {

        val user = AppUser.create(
            uid = userId,
            login = login,
            rawPassword = password,
            roles = roles
        )

        every { userRepository.getUser(userId) } returns user
        every { userRepository.deleteUser(userId) } returns null

        assertThrows<UserManagementException.FailedToDeleteUserException> {
            userService.deleteUser(userId)
        }
    }
}
