package org.cotrico.api.meter

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class CreateMeterDto(val serialNumber: String, val initialIndex: BigDecimal?, val readingDate: LocalDateTime)

data class CreateMeterReadingDto(val meterId: UUID, val currentIndex: BigDecimal, val readingDate: LocalDateTime)

data class MeterStatistics(val currentIndex: BigDecimal?, val lastIndex: BigDecimal?, val consumption: BigDecimal?)