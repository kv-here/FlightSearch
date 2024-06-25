package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("select * from airport")
    fun getAllAirports() : Flow<List<Airport>>

    @Query("select * from airport where id != :id")
    fun getAllAirportsExcept(id : Int) : Flow<List<Airport>>

    @Query("select * from airport where iata_code = :query or name like '%' ||:query||'%' order by passengers desc")
    fun getAllAirportsWith(query : String) : Flow<List<Airport>>

    @Query("select * from airport where iata_code = :code")
    fun getAirportWithCode(code : String): Airport

    @Query("select a.id as id, a.iata_code as code, a.name as name , case " +
            "when exists (select 1 from favorite f" +
            " where(f.departure_code = :from and f.destination_code = a.iata_code))" +
            "then 1 else 0 end as isFavorite " +
            "from airport a where a.iata_code != :from")
    fun getAirportWithFavoriteCheck(from: String): Flow<List<AirportWithFavoriteCheck>>

    @Insert
    suspend fun insertAirport(airport: Airport)
}