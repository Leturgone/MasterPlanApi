package api.masterplan.app.filesModule.infrastructure.filesStorage.repository

import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path

@Repository
class FIleStorageRepositoryImpl(
    private val storageFolder: Path
): FIleStorageRepository {
    override fun isFileExist(fileName: String): Boolean {
        val file = storageFolder.resolve(fileName)
        return Files.exists(file)
    }

    override fun writeFile(fileName: String, fileBytes: ByteArray): String? {
       return try {
           // Формирование пути к файлу
           val filePath = storageFolder.resolve(fileName)
           Files.write(filePath, fileBytes)
           filePath.toString()
       }catch (_:java.io.IOException){
           null
       }
    }

    override fun readFile(fileName: String): ByteArray? {
        return try {
            // Формирование пути к файлу
            val filePath = storageFolder.resolve(fileName)
            Files.readAllBytes(filePath)
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