package org.cotrico.api.housingUnit

import jakarta.transaction.Transactional
import org.cotrico.api.building.BuildingRepository
import org.cotrico.api.housingUnit.mappers.AssignMeterDto
import org.cotrico.api.housingUnit.mappers.BuildingResponse
import org.cotrico.api.housingUnit.mappers.CreateHousingUnitDto
import org.cotrico.api.housingUnit.mappers.HousingUnitDetailsResponse
import org.cotrico.api.housingUnit.mappers.HousingUnitListItemResponse
import org.cotrico.api.housingUnit.mappers.MeterItemResponse
import org.cotrico.api.meter.MeterRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class HousingUnitService(
    val housingUnitRepository: HousingUnitRepository,
    val buildingRepository: BuildingRepository,
    val meterRepository: MeterRepository,
) {

    fun loadAll() = housingUnitRepository
        .findAll()
        .map {
            HousingUnitListItemResponse(
                id = it.id,
                name = it.name,
                type = it.type,
            )
        }

    @Transactional
    fun loadById(id: UUID): HousingUnitDetailsResponse? {
        val housingUnit = housingUnitRepository.findHousingUnitWithMeters(id) ?: return null

        val building = BuildingResponse(
            id = housingUnit.building.id,
            name = housingUnit.building.name,
        )

        val meters = housingUnit.meters.map {
            MeterItemResponse(
                id = it.id,
                serialNumber = it.serialNumber,
            )
        }

        return HousingUnitDetailsResponse(
            id = housingUnit.id,
            name = housingUnit.name,
            type = housingUnit.type,
            meters = meters,
            building = building,
        )
    }

    @Transactional fun create(housingUnit: CreateHousingUnitDto) = buildingRepository
        .findById(housingUnit.buildingId)
        .orElse(null)?.let {
            building -> housingUnitRepository.save(HousingUnit(
                name = housingUnit.name,
                type = housingUnit.type,
                building = building,
            )
        )}

    @Transactional fun assignMeter(input: AssignMeterDto) {
        val meter = meterRepository.findById(input.meterId).orElseThrow{
            IllegalArgumentException("Meter with id ${input.meterId} not found")
        }
        val housingUnit = housingUnitRepository.findById(input.housingUnitId).orElseThrow{
            IllegalArgumentException("Housing unit ${input.housingUnitId} not found")
        }

        meter.housingUnit = housingUnit
    }
}