package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Auth Error response")
data class ErrorResponse(

    @Schema(description = "HTTP error code", example = "500")
    val status: Int,

    @Schema(description = "HTTP error description", example = "Error while login")
    val message: String,

    @Schema(description = "Error date", example = "2026-01-27T18:30:00Z")
    val timestamp: LocalDateTime,
)
