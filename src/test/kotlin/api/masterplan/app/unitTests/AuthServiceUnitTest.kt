package api.masterplan.app.unitTests

import api.masterplan.app.authModule.application.ports.UserCredentialsProvider
import api.masterplan.app.authModule.application.service.AuthServiceImpl
import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.domain.exception.AuthException
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import api.masterplan.app.authModule.domain.model.value.AuthUserRole
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AuthServiceUnitTest {

    // Данные для моков
    private val login = AuthUserLogin("login")
    private val password = AuthUserPassword("password")
    private val userId = AuthUserId(UUID.randomUUID())
    private val roles = setOf(AuthUserRole.ADMIN, AuthUserRole.EMPLOYEE)


    private val userCredentialsProvider = mockk<UserCredentialsProvider>()
    private val authService = AuthServiceImpl(userCredentialsProvider)

    @Test
    fun `authenticate success when credentials are valid`() {
        val expectedCredentials = UserAuthCredentials(
            userId = userId,
            authUserName = login,
            roles = roles
        )

        every { userCredentialsProvider.validateCredentials(login, password) } returns expectedCredentials
        val result = authService.authenticate(login, password)
        assertEquals(userId, result.userId)
        assertEquals(login, result.authUserName)
        assertEquals(roles, result.roles)
    }

    @Test
    fun `authenticate throw InvalidCredentials when credentials are invalid`() {
        every { userCredentialsProvider.validateCredentials(login, password) } throws
                AuthException.InvalidCredentials()
        assertThrows<AuthException.InvalidCredentials> {
            authService.authenticate(login, password)
        }
    }

    @Test
    fun `authenticate throw UserNotExistsWithLogin when user not found`() {
        every { userCredentialsProvider.validateCredentials(login, password) } throws
                AuthException.UserNotExistsWithLogin(login)
        assertThrows<AuthException.UserNotExistsWithLogin> {
            authService.authenticate(login, password)
        }
    }


    @Test
    fun `authenticate handle empty login`() {
        assertThrows<AuthException.InvalidLoginOrPassword> {
            AuthUserLogin.validate("")
        }
    }

    @Test
    fun `authenticate handle empty password`() {
        assertThrows<AuthException.InvalidLoginOrPassword> {
            AuthUserPassword.validate("")
        }
    }
}
