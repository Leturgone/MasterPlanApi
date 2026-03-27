package api.masterplan.app.filesModule.presentation.mapper

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.presentation.dto.response.FileResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

object FilesDomainToResponseMapper {
    fun toFileResponse(file: DocumentFileDetails): FileResponse{
        val contentType = "application/pdf"

        val headers = HttpHeaders()
        val safeFilename = transliterate(file.fileName.value)
        headers.contentType = MediaType.parseMediaType(contentType)
        headers.set("Content-ID", file.fileId.value.toString())
        headers.setContentDispositionFormData("attachment",safeFilename)
        headers.contentLength = file.fileData.value.size.toLong()

        return FileResponse(
            fileHeaders = headers,
            fileData = file.fileData.value
        )
    }

    private fun transliterate(text: String): String {
        val transliterationMap = hashMapOf(
            'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Д' to "D",
            'Е' to "E", 'Ё' to "Yo", 'Ж' to "Zh", 'З' to "Z", 'И' to "I",
            'Й' to "Y", 'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N",
            'О' to "O", 'П' to "P", 'Р' to "R", 'С' to "S", 'Т' to "T",
            'У' to "U", 'Ф' to "F", 'Х' to "Kh", 'Ц' to "Ts", 'Ч' to "Ch",
            'Ш' to "Sh", 'Щ' to "Sh", 'Ы' to "Y", 'Э' to "E",
            'Ю' to "Yu", 'Я' to "Ya",
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "yo", 'ж' to "zh", 'з' to "z", 'и' to "i",
            'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch",
            'ш' to "sh", 'щ' to "sh", 'ы' to "y", 'э' to "e",
            'ю' to "yu", 'я' to "ya"
        )
        return buildString {
            for (char in text){
                this.append(transliterationMap[char]?: char)
            }
        }
    }
}