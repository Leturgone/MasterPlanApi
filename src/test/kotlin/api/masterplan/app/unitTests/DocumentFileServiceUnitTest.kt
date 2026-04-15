package api.masterplan.app.unitTests

import api.masterplan.app.filesModule.application.mapper.FilesToDetailsMapper
import api.masterplan.app.filesModule.application.service.DocumentFileServiceImpl
import api.masterplan.app.filesModule.domain.exceptions.FilesException
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileRepository
import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DocumentFileServiceUnitTest {

    // Данные для моков
    private val fileId = DocumentFileId.generate()
    private val baseName = DocumentFileBaseName("test-file")
    private val updatedBaseName = DocumentFileBaseName("updated-file")
    private val fileData = DocumentFileData(byteArrayOf(0x01, 0x02, 0x03))
    private val fileName = DocumentFileName("test-file_20231010_123456")

    private val documentFileRepository = mockk<DocumentFileRepository>()
    private val documentFileService = DocumentFileServiceImpl(documentFileRepository)

    @Test
    fun `uploadFile upload new file successfully`() {
        every { documentFileRepository.saveFile(any()) } returns fileId

        val result = documentFileService.uploadFile(baseName, fileData)

        assertEquals(fileId, result)
    }

    @Test
    fun `uploadFile throw FailedToCreateFile when save fails`() {
        every { documentFileRepository.saveFile(any()) } returns null

        assertThrows<FilesException.FailedToCreateFile> {
            documentFileService.uploadFile(baseName, fileData)
        }
    }

    @Test
    fun `removeFile remove file successfully`() {
        val documentFile = DocumentFile.create(
            id = fileId,
            fileName = fileName,
            fileData = fileData
        )

        every { documentFileRepository.getFile(fileId) } returns documentFile
        every { documentFileRepository.removeFile(fileId, fileName) } returns fileId

        val result = documentFileService.removeFile(fileId)

        assertEquals(fileId, result)
    }

    @Test
    fun `removeFile throw FileNotExist when file does not exist`() {
        every { documentFileRepository.getFile(fileId) } returns null

        assertThrows<FilesException.FileNotExist> {
            documentFileService.removeFile(fileId)
        }
    }

    @Test
    fun `removeFile throw FailedToDeleteFile when delete fails`() {

        val documentFile = DocumentFile.create(
            id = fileId,
            fileName = fileName,
            fileData = fileData
        )

        every { documentFileRepository.getFile(fileId) } returns documentFile
        every { documentFileRepository.removeFile(fileId, fileName) } returns null

        assertThrows<FilesException.FailedToDeleteFile> {
            documentFileService.removeFile(fileId)
        }
    }

    @Test
    fun `updateFile update file successfully`() {
        val oldFile = DocumentFile.create(
            id = fileId,
            fileName = fileName,
            fileData = fileData
        )

        every { documentFileRepository.getFile(fileId) } returns oldFile
        every {
            documentFileRepository.updateFile(
                fileId = fileId,
                oldFileName = fileName,
                updatedDocumentFile = any()
            )
        } returns fileId

        val result = documentFileService.updateFile(fileId, updatedBaseName, fileData)

        assertEquals(fileId, result)
    }

    @Test
    fun `updateFile throw FileNotExist when file does not exist`() {
        every { documentFileRepository.getFile(fileId) } returns null

        assertThrows<FilesException.FileNotExist> {
            documentFileService.updateFile(fileId, updatedBaseName, fileData)
        }
    }

    @Test
    fun `updateFile throw FailedToUpdateFile when update fails`() {
        val oldFile = DocumentFile.create(
            id = fileId,
            fileName = fileName,
            fileData = fileData
        )
        val updatedFile = oldFile.update(updatedBaseName, fileData)

        every { documentFileRepository.getFile(fileId) } returns oldFile
        every {
            documentFileRepository.updateFile(
                fileId = fileId,
                oldFileName = fileName,
                updatedDocumentFile = updatedFile
            )
        } returns null

        assertThrows<FilesException.FailedToUpdateFile> {
            documentFileService.updateFile(fileId, updatedBaseName, fileData)
        }
    }

    @Test
    fun `downloadFile return file details when file exists`() {
        val documentFile = DocumentFile.create(
            id = fileId,
            fileName = fileName,
            fileData = fileData
        )
        val expectedDetails = FilesToDetailsMapper.toDocumentFileDetails(documentFile)

        every { documentFileRepository.getFile(fileId) } returns documentFile

        val result = documentFileService.downloadFile(fileId)

        assertEquals(expectedDetails, result)
    }

    @Test
    fun `downloadFile throw FileNotExist when file does not exist`() {
        every { documentFileRepository.getFile(fileId) } returns null

        assertThrows<FilesException.FileNotExist> {
            documentFileService.downloadFile(fileId)
        }
    }
}