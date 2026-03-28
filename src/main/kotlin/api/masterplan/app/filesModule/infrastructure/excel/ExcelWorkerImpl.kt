package api.masterplan.app.filesModule.infrastructure.excel

import api.masterplan.app.export.util.DisplayNameUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.full.memberProperties

@Component
class ExcelWorkerImpl(): ExcelWorker {
    override fun <T: Any> exportListToExcel(data: List<T>): ByteArray {
        if (data.isEmpty()) return  createEmptyWorkbook()

        // Создание книги
        val workbook = XSSFWorkbook()
        // Создание листа
        val sheet = workbook.createSheet()

        // Создание заголовка
        val headerRow = sheet.createRow(0)

        // Используем рефлексию
        val kClass = data.first()::class

        val classDisplayNames = DisplayNameUtil.getFieldNamesMap(kClass)

        // Получаем поля класса
        val classFields = kClass.memberProperties.sortedByDescending { it.name }


        classFields.forEachIndexed { index, field->
            val fieldName = field.name
            val displayName = classDisplayNames[fieldName]
            headerRow.createCell(index).setCellValue(displayName)
        }

        // Заполнение данными
        data.forEachIndexed {  rowIndex,objectItem ->
            // Создание строки +1 так как верхняя занята
            val row = sheet.createRow( rowIndex+1)
            classFields.forEachIndexed { columnIndex, field ->
                val value = field.getter.call(objectItem)
                val formattedForExelValue = getFormattedValue(value)
                // Создание ячейки столбца для каждого значения
                row.createCell(columnIndex).setCellValue(formattedForExelValue)
            }
        }

        // Автоширина столбцов
        classFields.indices.forEach { sheet.autoSizeColumn(it)}

        return workbook.toByteArray()
    }

    private fun createEmptyWorkbook(): ByteArray {
        val workbook = XSSFWorkbook()
        workbook.createSheet("Empty Workbook")
        return workbook.toByteArray()
    }

    private fun XSSFWorkbook.toByteArray(): ByteArray {
        val workbook = this
        return ByteArrayOutputStream().use { outputStream ->
            workbook.write(outputStream)
            outputStream.toByteArray()
        }
    }



    private fun getFormattedValue(value: Any?): String{
        return when(value){
            null -> ""
            is LocalDateTime -> value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            is LocalDate -> value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            is Double -> String.format(Locale.getDefault(), "%,.2f", value)
            is Float -> String.format(Locale.getDefault(), "%,.2f", value)
            is List<*> -> value.joinToString(",")
            else -> value.toString()
        }
    }
}