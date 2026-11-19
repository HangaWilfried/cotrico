package org.cotrico.api.building.mappers

import java.util.UUID

data class BuildingListItemResponse(
    val id: UUID?,
    val name: String,
    val location: String?,
)

data class HousingUnitListItemResponse(
    val id: UUID?,
    val name: String,
)

data class BuildingDetailsResponse(
    val id: UUID?,
    val name: String,
    val location: String?,
    val description: String?,
    val housingUnits: List<HousingUnitListItemResponse>,
)