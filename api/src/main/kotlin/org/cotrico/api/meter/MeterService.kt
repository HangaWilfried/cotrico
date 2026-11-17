package org.cotrico.api.meter

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Service
class MeterService(val meterRepository: MeterRepository, val meterReadingRepository: MeterReadingRepository) {
    fun fetchAllMeters() = meterRepository.findAll()

    fun findMeterById(id: UUID) = meterRepository.findById(id)

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
                    require(input.readingDate.isAfter(previous.readingDate))
                    require(input.currentIndex >= previous.indexValue)

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

    private fun saveMeterReading(meter: Meter, index: BigDecimal, readingDate: LocalDateTime) {
        val meterReading = MeterReading(meter, index, readingDate)
        meterReadingRepository.save(meterReading)
    }

}