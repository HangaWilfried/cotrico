package org.cotrico.api.meter.mappers

import java.math.BigDecimal
import java.util.UUID

data class CreateMeterDto(val serialNumber: String, val initialIndex: BigDecimal?, val readingDate: String)

data class CreateMeterReadingDto(val currentIndex: BigDecimal, val readingDate: String)

data class MeterStatistics(val currentIndex: BigDecimal?, val lastIndex: BigDecimal?, val consumption: BigDecimal?)
