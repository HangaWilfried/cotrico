package org.cotrico.api.meter

import jakarta.transaction.Transactional
import org.cotrico.api.meter.mappers.CreateMeterDto
import org.cotrico.api.meter.mappers.CreateMeterReadingDto
import org.cotrico.api.meter.mappers.MeterReadingResponse
import org.cotrico.api.meter.mappers.MeterDetailResponse
import org.cotrico.api.meter.mappers.MeterListItemResponse
import org.cotrico.api.meter.mappers.MeterStatistics
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class MeterService(val meterRepository: MeterRepository, val meterReadingRepository: MeterReadingRepository) {
    fun fetchAllMeters() = meterRepository.findAll().map {
        MeterListItemResponse(
            id = it.id,
            serialNumber = it.serialNumber,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }

    @Transactional()
    fun findMeterDetails(id: UUID): MeterDetailResponse? {
        val meter = meterRepository.findMeterWithReadings(id) ?: return null

        return MeterDetailResponse(
            id = meter.id,
            serialNumber = meter.serialNumber,
            readings = meter.readings.map {
                MeterReadingResponse(
                    id = it.id,
                    indexValue = it.indexValue,
                    readingDate = it.readingDate
                )
            }
        )
    }

    @Transactional fun createMeter(input: CreateMeterDto): UUID? {
        val meter = meterRepository.save(Meter(input.serialNumber))
        input.initialIndex?.let { saveMeterReading(meter, it, input.readingDate) }

        return meter.id
    }

    @Transactional fun createMeterReading(input: CreateMeterReadingDto) {
        val meter = meterRepository.findByIdForUpdate(input.meterId)

        meter?.let {
           val lastReading = meterReadingRepository.findFirstByMeterIdOrderByReadingDateDesc(input.meterId)
           lastReading?.let {
                previous -> {
                    val newReadingDate = LocalDateTime.parse(input.readingDate)

                    require(input.currentIndex >= previous.indexValue)
                    require(newReadingDate.isAfter(previous.readingDate))

                    saveMeterReading(it, input.currentIndex, input.readingDate)
                }
           }
        }
    }

    @Transactional fun loadCurrentConsumption(meterId: UUID) : MeterStatistics {
        val readings = meterReadingRepository.findTop2ByMeterIdOrderByReadingDateDesc(meterId)

        val currentIndex = readings.firstOrNull()?.indexValue
        val lastIndex = readings.lastOrNull()?.indexValue

        val consumption = if (lastIndex != null && currentIndex != null) {
            currentIndex - lastIndex
        } else {
            null
        }

        return MeterStatistics(currentIndex, lastIndex, consumption)
    }

    private fun saveMeterReading(meter: Meter, index: BigDecimal, readingDate: String) {
        val meterReading = MeterReading(meter, index, LocalDateTime.parse(readingDate))
        meterReadingRepository.save(meterReading)
    }

}