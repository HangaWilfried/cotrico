package org.cotrico.api.meter.mappers

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class MeterListItemResponse(
    val id: UUID?,
    val serialNumber: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

data class MeterReadingResponse(
    val id: UUID?,
    val indexValue: BigDecimal,
    val readingDate: LocalDateTime
)

data class MeterDetailResponse(
    val id: UUID?,
    val serialNumber: String,
    val readings: List<MeterReadingResponse>
)