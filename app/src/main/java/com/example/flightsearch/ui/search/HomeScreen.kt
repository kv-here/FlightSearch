package com.example.flightsearch.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.screens.FavoritesScreen
import com.example.flightsearch.ui.screens.SearchResultsScreen
import com.example.flightsearch.ui.screens.SearchSuggestionScreen

@Composable
fun SearchSpace(
    viewModel: SearchBarViewModel = viewModel(factory = SearchBarViewModel.factory),
    modifier: Modifier = Modifier
){
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        SearchBar(
            value = when(uiState){
                is SearchState.Favourites -> ""
                is SearchState.Searching -> uiState.query
                is SearchState.Results -> uiState.query },
            onValueChange = { viewModel.changeInput(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Box(modifier = Modifier.weight(1f)){
            when(uiState){
                is SearchState.Searching -> {
                    val suggestions by uiState.suggestions.collectAsState(initial = listOf())
                    SearchSuggestionScreen(
                        suggestions = suggestions,
                        onClick = viewModel::setText

                    )
                }
                is SearchState.Results -> {
                    val results by uiState.to.collectAsState(initial = listOf())
                    SearchResultsScreen(
                        from = uiState.from,
                        results = results,
                        addToFavorite = viewModel::addToFavorite,
                        removeFromFavorite = viewModel::removeFromFavorite,
                        coroutineScope = coroutineScope,
                    )
                }
                is SearchState.Favourites->{
                    val favoriteRoutes by uiState.routes.collectAsState(initial= listOf())
                    FavoritesScreen(
                        routes = favoriteRoutes,
                        coroutineScope = coroutineScope,
                        removeFromFavorite = viewModel::deleteFavorite
                    )
                }
            }

        }
    }

}

@Composable
fun SearchBar(
    value : String,
    onValueChange : (String )-> Unit,
    modifier: Modifier = Modifier
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = "Search")},
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        modifier = modifier
    )
}