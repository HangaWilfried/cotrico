package org.cotrico.api.building

import org.cotrico.api.building.mappers.CreateBuildingDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/building")
class BuildingController(val buildingService: BuildingService) {
    @GetMapping fun fetchAllBuildings() = buildingService.fetchAll()

    @PostMapping fun createBuilding(@RequestBody input: CreateBuildingDto) = buildingService.createBuilding(input)

    @GetMapping("/{id}")
    fun fetchBuildingById(@PathVariable id: UUID) = buildingService.fetchById(id)
}