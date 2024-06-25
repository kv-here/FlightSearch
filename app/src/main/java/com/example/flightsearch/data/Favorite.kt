package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.util.TableInfo

@Entity(
    tableName = "favorite",
//    foreignKeys = [
//        ForeignKey(
//            entity = Airport::class,
//            parentColumns = ["iata_code"],
//            childColumns = ["departure_code"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Airport::class,
//            parentColumns = ["iata_code"],
//            childColumns = ["destination_code"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode : String,
    @ColumnInfo(name = "destination_code")
    val destinationCode : String
)
