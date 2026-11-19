package org.cotrico.api.housingUnit

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.LockModeType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.cotrico.api.BaseEntity
import org.cotrico.api.building.Building
import org.cotrico.api.housingUnit.mappers.HousingUnitType
import org.cotrico.api.meter.Meter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

@Entity
@Table(
    name = "housing_units",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_housing_unit_name_building",
            columnNames = ["name", "building_id"]
        )
    ]
)
class HousingUnit (
    @Column(nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: HousingUnitType,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    var building: Building,

    @OneToMany(mappedBy = "housingUnit")
    var meters: MutableList<Meter> = mutableListOf()
) : BaseEntity()

interface HousingUnitRepository : JpaRepository<HousingUnit, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT h FROM HousingUnit h LEFT JOIN FETCH h.meters WHERE h.id = :id")
    fun findHousingUnitWithMeters(@Param("id") housingUnitId: UUID): HousingUnit?
}