package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Auth Success response")
data class LoginResponse(

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
)
