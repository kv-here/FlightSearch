package com.example.flightsearch.ui.search



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportWithFavoriteCheck
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteAndAirports
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface SearchState{
    data class Favourites(
        val routes : Flow<List<FavoriteAndAirports>>
    ) : SearchState

    data class Searching (
        val query : String,
        val suggestions: Flow<List<Airport>>
    ): SearchState
    data class Results (
        val query : String,
        val from : Airport,
        val to :  Flow<List<AirportWithFavoriteCheck>>
    ) : SearchState
}


class SearchBarViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository

) : ViewModel(){
     var uiState : SearchState by mutableStateOf(SearchState.Favourites(routes = getAllFavorites()))

    init {
        viewModelScope.launch {
            val searchValue = userPreferencesRepository.searchBarValue.first()
            changeInput(searchValue)
        }

    }

    fun changeInput(input : String){
        viewModelScope.launch {
            userPreferencesRepository.saveLayoutPreference(input)
        }
        uiState =
            if(input.isEmpty()){
                    SearchState.Favourites(routes = getAllFavorites())
        } else{
                    SearchState.Searching(query = input, suggestions = getSearchSuggestions(input))
        }
    }

    fun setText(from : Airport){
        uiState =
            SearchState.Results(
            query = from.code,
            from = from,
            to = getAirportWithFavoriteCheck(from.code)
        )
    }

    private fun getAirportWithFavoriteCheck(from: String): Flow<List<AirportWithFavoriteCheck>>{
        return flightRepository.getAirportWithFavoriteCheckStream(from)
    }

    private fun getAllFavorites():Flow<List<FavoriteAndAirports>>{
        return flightRepository.getAllFavoritesAndAirportsStream()
    }

    private fun getSearchSuggestions(query: String): Flow<List<Airport>> {
        return flightRepository.getAllAirportsStreamWith(query)

    }


    suspend fun addToFavorite(from : String, to : String){
        flightRepository.checkAndInsert(from, to)
    }

    suspend fun removeFromFavorite(departureCode : String, destinationCode : String){
        flightRepository.removeFromFavourite(departureCode, destinationCode)
    }

    suspend fun deleteFavorite(route : Favorite){
        flightRepository.deleteFavorite(route)
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                SearchBarViewModel(
                    flightRepository = application.container.flightRepository,
                    userPreferencesRepository = application.userPreferencesRepository
                )
            }
        }
    }
}

