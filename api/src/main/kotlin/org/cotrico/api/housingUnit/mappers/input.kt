package org.cotrico.api.housingUnit.mappers

import java.util.UUID

enum class HousingUnitType {
    ROOM,
    STUDIO,
    OTHER
}

data class CreateBuildingDto(val name: String, val description: String?, val location: String?)

data class CreateHousingUnitDto(val name: String, val buildingId: UUID, val meterId: UUID, val type: HousingUnitType)