package org.cotrico.api.housingUnit

import org.cotrico.api.housingUnit.mappers.AssignMeterDto
import org.cotrico.api.housingUnit.mappers.CreateHousingUnitDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/housing-unit")
class HousingUnitController(val housingUnitService: HousingUnitService) {
    @GetMapping
    fun fetchAllHousingUnits() = housingUnitService.loadAll()

    @PostMapping
    fun createHousingUnit(@RequestBody input: CreateHousingUnitDto) = housingUnitService.create(input)

    @GetMapping("/{id}")
    fun fetchHouseUnitById(@PathVariable("id") id: UUID) = housingUnitService.loadById(id)

    @PutMapping("/{id}/meter/{meterId}")
    fun assignMeter(
        @PathVariable("id") id: UUID,
        @PathVariable("meterId") meterId: UUID
    ) = housingUnitService.assignMeter(
        AssignMeterDto(housingUnitId = id, meterId = meterId)
    )
}