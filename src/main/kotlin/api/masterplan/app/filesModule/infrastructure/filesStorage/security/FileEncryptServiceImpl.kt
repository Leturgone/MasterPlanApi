package api.masterplan.app.filesModule.infrastructure.filesStorage.security

import org.springframework.stereotype.Service
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

@Service
class FileEncryptServiceImpl(
    private val secretKey: SecretKey
): FileEncryptService {

    override fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }

    override fun decrypt(encryptedData: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = encryptedData.copyOfRange(0, 16)
        val actualEncryptedData = encryptedData.copyOfRange(16, encryptedData.size)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        return cipher.doFinal(actualEncryptedData)
    }

}