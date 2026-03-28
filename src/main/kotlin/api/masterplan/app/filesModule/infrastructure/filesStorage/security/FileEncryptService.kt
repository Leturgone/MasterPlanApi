package api.masterplan.app.filesModule.infrastructure.filesStorage.security

interface FileEncryptService {

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(encryptedData: ByteArray): ByteArray
}