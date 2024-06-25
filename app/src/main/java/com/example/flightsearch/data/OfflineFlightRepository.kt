package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class OfflineFlightRepository(
    private val airportDao : AirportDao,
    private val favouriteDao: FavouriteDao
): FlightRepository {
    override fun getAllAirportsStream(): Flow<List<Airport>> = airportDao.getAllAirports()

    override fun getAllAirportsStreamExcept(id: Int): Flow<List<Airport>> = airportDao.getAllAirportsExcept(id)

    override fun getAllAirportsStreamWith(query: String): Flow<List<Airport>> =
        airportDao.getAllAirportsWith(query)

    override fun getAllFavouritesStream(): Flow<List<Favorite>> = favouriteDao.getAllFavourites()

    override fun routeExists(departureCode: String, destinationCode: String): Boolean =
        favouriteDao.routeExists(departureCode, destinationCode)

    override fun giveRoute(departureCode: String, destinationCode: String): Flow<Favorite?> =
        favouriteDao.giveRoute(departureCode, destinationCode)

    override fun getAllFavoritesAndAirportsStream(): Flow<List<FavoriteAndAirports>>
    = favouriteDao.getAllFavoritesAndAirports()

    override fun getAirportWithFavoriteCheckStream(from: String): Flow<List<AirportWithFavoriteCheck>> =
        airportDao.getAirportWithFavoriteCheck(from)

    override suspend fun insertFavourite(route: Favorite) = favouriteDao.insertFavourite(route)

    override suspend fun removeFromFavourite(departureCode : String, destinationCode : String){

        if(routeExists(departureCode, destinationCode)){
            val item = favouriteDao.giveRoute(departureCode, destinationCode).first()
            favouriteDao.deleteFavourite(item)
        }
    }

    override suspend fun deleteFavorite(route: Favorite) = favouriteDao.deleteFavourite(route)

    override fun getAirportWithCode(code: String): Airport = airportDao.getAirportWithCode(code)

    override suspend fun checkAndInsert(departureCode: String, destinationCode: String){
        if(!routeExists(departureCode, destinationCode)){
            insertFavourite(Favorite(departureCode =  departureCode, destinationCode =  destinationCode))
        }
    }

}