package org.cotrico.api.building

import jakarta.transaction.Transactional
import org.cotrico.api.building.mappers.BuildingDetailsResponse
import org.cotrico.api.building.mappers.BuildingListItemResponse
import org.cotrico.api.building.mappers.CreateBuildingDto
import org.cotrico.api.building.mappers.HousingUnitListItemResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BuildingService(val buildingRepository: BuildingRepository) {
    fun fetchAll() = buildingRepository.findAll().map {
        building -> BuildingListItemResponse(
            id = building.id,
            name = building.name,
            location = building.location
        )
    }

    @Transactional fun fetchById(id: UUID): BuildingDetailsResponse? {
        val building = buildingRepository.findBuildingWithHouseUnits(id) ?: return null

        val housingUnits = building.houseUnits.map {
            houseUnit ->  HousingUnitListItemResponse(
                id = houseUnit.id,
                name = houseUnit.name,
            )
        }

        return BuildingDetailsResponse(
            id = building.id,
            name = building.name,
            location = building.location,
            description = building.description,
            housingUnits = housingUnits
        )
    }

    fun createBuilding(input: CreateBuildingDto): UUID? {
        val building = buildingRepository.save(Building(input.name, input.description, input.location))
        return building.id
    }
}