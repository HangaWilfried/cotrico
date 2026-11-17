package org.cotrico.api.meter.mappers

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

interface MeterResponseDto

data class MeterResponse(
    val id: UUID?,
    val serialNumber: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) : MeterResponseDto

data class MeterReadingResponse(
    val id: UUID?,
    val indexValue: BigDecimal,
    val readingDate: LocalDateTime
)

data class MeterDetailResponse(
    val id: UUID?,
    val serialNumber: String,
    val readings: List<MeterReadingResponse>
) : MeterResponseDto