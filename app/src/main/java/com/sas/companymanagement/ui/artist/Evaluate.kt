package com.sas.companymanagement.ui.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "evaluate_tbl")
data class Evaluate (
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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "evaluateId")
    var id: Int = 0
)
