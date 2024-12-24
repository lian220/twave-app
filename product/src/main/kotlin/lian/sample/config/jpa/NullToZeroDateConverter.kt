package kr.co.mustit.jpa

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Converter
class NullToZeroDateConverter: AttributeConverter<Instant?, String> {
  override fun convertToDatabaseColumn(attribute: Instant?): String {
    val formatter = DateTimeFormatter
      .ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" )
      .withLocale( Locale.KOREA )
      .withZone( ZoneId.of("Asia/Seoul"))
    return attribute?.let { formatter.format(attribute) } ?: "0000-00-00 00:00:00.000"
  }

  override fun convertToEntityAttribute(dbData: String): Instant? {
    val dbDataSb = StringBuilder(dbData)
    if (dbData.length < 23) {
      for (i in 0 until 23 - dbData.length) {
        if (dbDataSb.length == 19) {
          dbDataSb.append(".")
          continue
        }
        dbDataSb.append("0")
      }
    }
    return if (dbDataSb.toString() == "0000-00-00 00:00:00.000") null else timeParsing(dbDataSb.toString(), "yyyy-MM-dd HH:mm:ss.SSS")
  }

  private fun timeParsing(target: String, pattern: String): Instant {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.KOREA)
    return LocalDateTime.parse(target, formatter).atZone(ZoneId.of("Asia/Seoul"))
      .toInstant()
  }
}
