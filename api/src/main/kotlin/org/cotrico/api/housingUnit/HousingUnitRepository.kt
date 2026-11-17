package org.cotrico.api.housingUnit

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.cotrico.api.BaseEntity
import org.cotrico.api.meter.Meter

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