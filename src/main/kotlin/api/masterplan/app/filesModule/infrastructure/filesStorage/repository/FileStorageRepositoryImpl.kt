package api.masterplan.app.filesModule.infrastructure.filesStorage.repository

import api.masterplan.app.filesModule.infrastructure.filesStorage.security.FileEncryptService
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path

@Repository
class FileStorageRepositoryImpl(
    private val fileEncryptService: FileEncryptService,
    private val storageFolder: Path
): FileStorageRepository {
    override fun isFileExist(fileName: String): Boolean {
        val file = storageFolder.resolve(fileName)
        return Files.exists(file)
    }

    override fun writeFile(fileName: String, fileBytes: ByteArray): String? {
       return try {
           // Формирование пути к файлу
           val filePath = storageFolder.resolve("$fileName.enc")
           val encryptedData = fileEncryptService.encrypt(fileBytes)
           Files.write(filePath, encryptedData)
           filePath.toString()
       }catch (_:java.io.IOException){
           null
       }
    }

    override fun readFile(fileName: String): ByteArray? {
        return try {
            // Формирование пути к файлу
            val filePath = storageFolder.resolve("$fileName.enc")
            val encryptedBytes = Files.readAllBytes(filePath)
            fileEncryptService.decrypt(encryptedBytes)
        }catch (_:java.io.IOException){
            null
        }
    }

    override fun delete(fileName: String): String? {
        return try {
            // Формирование пути к файлу
            val filePath = storageFolder.resolve(fileName)
            Files.delete(filePath)
            return filePath.toString()
        }catch (_:java.io.IOException){
            null
        }
    }

}