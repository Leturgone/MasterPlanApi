package api.masterplan.app.export.util

import api.masterplan.app.export.annotation.ExportDisplayName
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

object DisplayNameUtil {
    /**
     * Получает карту соответствия имён полей и их русских названий для класса.
     */
    fun <T : Any> getFieldNamesMap(clazz: KClass<T>): Map<String, String> {
        return clazz.memberProperties.associate { property ->
            val annotation = property.findAnnotation<ExportDisplayName>()
            property.name to (annotation?.value ?: property.name)
        }
    }

    /**
     * Получает русские названия для всех полей экземпляра класса.
     */
    fun <T: Any> getDisplayNames(instate: T): Map<String, String> {
        return getFieldNamesMap(instate::class)
    }

    /**
     * Получает русское название для конкретного поля.
     * Возвращает null, если поле не найдено или у него нет аннотации.
     */
    fun <T: Any> getDisplayName(instate: T,fieldName: String): String? {
        val field = instate::class.java.declaredFields.find { it.name == fieldName }
        return field?.getAnnotation(ExportDisplayName::class.java)?.value
    }

}