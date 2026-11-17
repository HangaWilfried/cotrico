package org.cotrico.api.meter

import org.cotrico.api.meter.mappers.CreateMeterDto
import org.cotrico.api.meter.mappers.CreateMeterReadingDto
import org.cotrico.api.meter.mappers.MeterResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@RestController
@RequestMapping("/meter")
class MeterController(val meterService: MeterService) {
    @GetMapping fun fetchAllMeters() = meterService.fetchAllMeters()

    @PostMapping fun createMeter(@RequestBody input: CreateMeterDto) = meterService.createMeter(input)

    @GetMapping("/{id}")
    fun fetchMeterById(@PathVariable id: UUID, @RequestParam(name = "fieldToExtractCode") extractCode: String?) = when(extractCode) {
        is String -> meterService.findMeterDetails(id)
        else -> meterService.findMeterById(id)
    }

    @GetMapping("/{id}/consumption") fun getMeterConsumption(@PathVariable id: UUID) = meterService.loadCurrentConsumption(id)

    @PutMapping("/{id}/consumption") fun createMeterReading(@RequestBody input: CreateMeterReadingDto) = meterService.createMeterReading(input)
}