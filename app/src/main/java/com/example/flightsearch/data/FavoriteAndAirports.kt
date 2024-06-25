package com.example.flightsearch.data

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteAndAirports(
    @Embedded val favorite: Favorite,

    @Relation(
        parentColumn = "departure_code",
        entityColumn = "iata_code"
    )
    val departureAirport: Airport,

    @Relation(
        parentColumn = "destination_code",
        entityColumn = "iata_code"
    )
    val destinationAirport: Airport
)

