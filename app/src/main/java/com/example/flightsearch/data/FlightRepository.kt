package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllAirportsStream() : Flow<List<Airport>>

    fun getAllAirportsStreamExcept(id : Int) : Flow<List<Airport>>

    fun getAllAirportsStreamWith(query : String) : Flow<List<Airport>>

    fun getAllFavouritesStream() : Flow<List<Favorite>>

    fun giveRoute(departureCode : String, destinationCode : String) : Flow<Favorite?>

    fun routeExists(departureCode : String, destinationCode : String) : Boolean

    suspend fun insertFavourite(route : Favorite)

    suspend fun removeFromFavourite(departureCode : String, destinationCode : String)

    suspend fun deleteFavorite(route: Favorite)

    fun getAirportWithCode(code : String): Airport

    suspend fun checkAndInsert(departureCode: String, destinationCode: String)

    fun getAirportWithFavoriteCheckStream(from: String): Flow<List<AirportWithFavoriteCheck>>

    fun getAllFavoritesAndAirportsStream():Flow<List<FavoriteAndAirports>>

}