package org.cotrico.api.housingUnit.mappers

import java.util.UUID

data class HousingUnitListItemResponse(
    val id: UUID?,
    val name: String,
    val type: HousingUnitType
)

data class BuildingResponse(val id: UUID?, val name: String)

data class MeterItemResponse(
    val id: UUID?,
    val serialNumber: String,
)

data class HousingUnitDetailsResponse(
    val id: UUID?,
    val name: String,
    val type: HousingUnitType,
    val building: BuildingResponse,
    val meter: MeterItemResponse?
)