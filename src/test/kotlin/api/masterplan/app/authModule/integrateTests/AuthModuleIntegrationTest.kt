package api.masterplan.app.authModule.integrateTests

import api.masterplan.app.authModule.presentation.dto.LoginRequest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

class AuthModuleIntegrationTest {
    @Test
    fun `GET users{id} should return user`() {
        val loginRequest = LoginRequest(
            "MASTERPLAN_LOGIN","MASTERPLAN_PASSWORD"
        )
        RestAssured.given()
            .contentType("application/json")
            .body(loginRequest)
            .post("/api/v1/auth/login")
            .then()
            .statusCode(401)
            .body(containsString("User"),
                containsString("is not exists"))
    }
}