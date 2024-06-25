package com.example.flightsearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.MockData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AirportDaoTest {
    private lateinit var airportDao: AirportDao
    private lateinit var appDatabase: FlightDatabase

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
        airportDao = appDatabase.airportDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB()= runBlocking {
        insertOne()
        val allItems = airportDao.getAllAirports().first()
        assertEquals( 34, allItems.size)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsWithNameFromDB()= runBlocking{
        insertFromMockData()
        val item = airportDao.getAllAirportsWith("OPO").first()
        assertEquals(MockData.mockAirportList[0], item[0])

        val items = airportDao.getAllAirportsWith("Air").first()
        assertEquals(MockData.mockAirportList, items)
    }

    suspend fun insertOne(){
        airportDao.insertAirport(airport1)
    }

    suspend fun insertFromMockData(){
        for (e in MockData.mockAirportList){
            airportDao.insertAirport(e)
        }
    }
}