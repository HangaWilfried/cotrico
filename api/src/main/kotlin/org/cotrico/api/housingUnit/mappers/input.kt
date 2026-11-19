package org.cotrico.api.housingUnit.mappers

import java.util.UUID

enum class HousingUnitType {
    ROOM,
    STUDIO,
    OTHER
}

data class CreateHousingUnitDto(val name: String, val buildingId: UUID, val type: HousingUnitType)

data class AssignMeterDto(val housingUnitId: UUID, val meterId: UUID)