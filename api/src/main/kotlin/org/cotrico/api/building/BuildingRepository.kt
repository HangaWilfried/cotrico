package org.cotrico.api.building

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.LockModeType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.cotrico.api.BaseEntity
import org.cotrico.api.housingUnit.HousingUnit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

@Entity
@Table(name = "buildings")
class Building (
    @Column(nullable = false)
    val name: String,

    @Column
    val description: String? = null,

    @Column
    val location: String? = null,

    @OneToMany(mappedBy = "building")
    var houseUnits: MutableList<HousingUnit> = mutableListOf()
) : BaseEntity()

interface BuildingRepository : JpaRepository<Building, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT b FROM Building b LEFT JOIN FETCH b.houseUnits where b.id = :id")
    fun findBuildingWithHouseUnits(@Param("id") id: UUID): Building?
}