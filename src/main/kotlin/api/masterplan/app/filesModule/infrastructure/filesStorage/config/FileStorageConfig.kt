package api.masterplan.app.filesModule.infrastructure.filesStorage.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Path
import java.nio.file.Paths
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableConfigurationProperties(StorageProperties::class)
class FileStorageConfig(
    private val storageProperties: StorageProperties
) {

    @Bean
    fun fileStorageDir(): Path{
        val filePath = Paths.get(storageProperties.path).toAbsolutePath()
        return filePath
    }

    @Bean
    fun encryptKey(): SecretKey {
        val stringKey = storageProperties.encryptKey
        val secretKey = SecretKeySpec(stringKey.toByteArray(), "AES")
        return secretKey
    }
}