package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Auth response")
sealed class LoginResponse private constructor() {

    @Schema(description = "Auth Success response")
    data class Success(

        @Schema(description = "JWT‑token for access API",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODk..."
        )
        val token: String,

        @Schema(
            description = "Token type",
            example = "Bearer",
            defaultValue = "Bearer"
        )
        val type: String = "Bearer",
    ): LoginResponse()

    @Schema(description = "Auth Error response")
    data class Error(

        @Schema(description = "HTTP error code", example = "500")
        val status: Int,

        @Schema(description = "HTTP error description", example = "Error while login")
        val message: String? = "",

        @Schema(description = "Error date", example = "2026-01-27T18:30:00Z")
        val timestamp: LocalDateTime,
    ): LoginResponse()

}



