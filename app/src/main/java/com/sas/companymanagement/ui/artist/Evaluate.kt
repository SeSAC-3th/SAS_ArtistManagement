package com.sas.companymanagement.ui.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "evaluate_tbl",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["id"],
            childColumns = ["artistId"],
        )
    ]
)
data class Evaluate(
    @ColumnInfo(name = "evalFan")
    var evalFan: Int = 0,

    @ColumnInfo(name = "evalExecutive")
    var evalExecutive: Int = 0,

    @ColumnInfo(name = "evalPotential")
    var evalPotential: Int = 0,

    @ColumnInfo(name = "evalRevenue")
    var evalRevenue: Int = 0,

    @ColumnInfo(name = "evalScandal")
    var evalScandal: Int = 0,

    @ColumnInfo(name = "artistId")
    var artistId: Long = 0L,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
)