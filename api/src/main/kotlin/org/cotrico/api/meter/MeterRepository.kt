package org.cotrico.api.meter

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.LockModeType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.cotrico.api.BaseEntity
import org.cotrico.api.housingUnit.HousingUnit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity @Table(
    name = "meter_readings",
    indexes = [
        Index(
            name = "idx_meter_reading_date",
            columnList = "meter_id, reading_date"
        )
    ]
) class MeterReading(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    var meter: Meter,

    @Column(name = "index_value", nullable = false, precision = 12, scale = 2)
    var indexValue: BigDecimal,

    @Column(name = "reading_date", nullable = false)
    var readingDate: LocalDateTime
) : BaseEntity()

@Entity @Table(
    name = "meters",
    indexes = [
        Index(
            name = "idx_meter_housing_unit",
            columnList = "housing_unit_id"
        )
    ]
) class Meter (
    @Column(name = "serial_number", nullable = false, unique = true)
    val serialNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "housing_unit_id")
    var housingUnit: HousingUnit? = null,

    @OneToMany(mappedBy = "meter")
    var readings: MutableList<MeterReading> = mutableListOf()
) : BaseEntity()

interface MeterRepository : JpaRepository<Meter, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Meter m WHERE m.id = :id")
    fun findByIdForUpdate(@Param("id") id: UUID): Meter?
}

interface MeterReadingRepository : JpaRepository<MeterReading, UUID> {
    fun findByMeterIdOrderByReadingDateDesc(meterId: UUID): List<MeterReading>
    fun findTop2ByMeterIdOrderByReadingDateDesc(meterId: UUID): List<MeterReading>
    fun findFirstByMeterIdOrderByReadingDateDesc(meterId: UUID): MeterReading?
}

