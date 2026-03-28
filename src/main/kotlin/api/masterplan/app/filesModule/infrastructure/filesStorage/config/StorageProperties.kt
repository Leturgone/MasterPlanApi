package api.masterplan.app.filesModule.infrastructure.filesStorage.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "storage")
data class StorageProperties(
    val path: String,
    val encryptKey: String
)
