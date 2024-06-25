package com.example.flightsearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavouriteDao
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.MockData.mockAirportList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var appDatabase: FlightDatabase
    private lateinit var favoriteDao : FavouriteDao

    private val airport1 : Airport = Airport(id=0,name= "test", code =  "TST", passengers =  5053134)

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        appDatabase = Room.inMemoryDatabaseBuilder(context, FlightDatabase::class.java)
            // Allowing main thread queries, just for testing.
//            .createFromAsset("database/flight_search.db")
            .allowMainThreadQueries()
            .build()
        favoriteDao = appDatabase.favouriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    val fav1 = Favorite(1,mockAirportList[0].code, mockAirportList[1].code)
    val fav2 = Favorite(2,mockAirportList[2].code, mockAirportList[3].code)

    suspend fun addToDB(){
        favoriteDao.insertFavourite(fav1 )
        favoriteDao.insertFavourite(fav2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInserttems_returnsIfItemInDB()= runBlocking{
        addToDB()
        val isTrue = favoriteDao.routeExists(mockAirportList[0].code, mockAirportList[1].code)
        Assert.assertEquals(true, isTrue)

        val isFalse = favoriteDao.routeExists(mockAirportList[1].code, mockAirportList[0].code)
        Assert.assertEquals(false, isFalse)

//        val item = favoriteDao.giveRoute(mockAirportList[0].code, mockAirportList[1].code).first()
//        Assert.assertEquals(fav1, item)

//        val item2 = favoriteDao.giveRoute(mockAirportList[1].code, mockAirportList[0].code).first()
//        Assert.assertEquals(null, item2)
    }
}