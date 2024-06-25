package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("select * from favorite")
    fun getAllFavourites() : Flow<List<Favorite>>

    @Query("select * from favorite where departure_code = :departureCode and destination_code = :destinationCode")
    fun giveRoute(departureCode : String, destinationCode : String) : Flow<Favorite>

    @Query("select exists(select * from favorite where departure_code = :departureCode and destination_code = :destinationCode)")
    fun routeExists(departureCode : String, destinationCode : String) : Boolean

    @Insert
    suspend fun insertFavourite(route : Favorite)

//    @Query ("delete from favorite where departure_code = :departureCode and destination_code = :destinationCode")
    @Delete
    suspend fun deleteFavourite(route : Favorite)

    @Transaction
    @Query("select * from favorite")
    fun getAllFavoritesAndAirports():Flow<List<FavoriteAndAirports>>
}