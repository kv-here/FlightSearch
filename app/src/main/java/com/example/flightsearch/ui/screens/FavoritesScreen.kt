package com.example.flightsearch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteAndAirports
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FavoritesScreen(
    routes : List<FavoriteAndAirports>,
    coroutineScope: CoroutineScope,
    removeFromFavorite : suspend(Favorite) ->Unit,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier){
        Text(
            text = "Favorite Routes",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(8.dp)
        )
        if (routes.isEmpty()){
            Text(text = "No favorite routes added yet")
        }
        else{
            FavoriteRoutesList(
                routes = routes,
                coroutineScope = coroutineScope,
                removeFromFavorite = removeFromFavorite
            )
        }
    }


}

@Composable
fun FavoriteRoutesList(
    routes : List<FavoriteAndAirports>,
    coroutineScope: CoroutineScope,
    removeFromFavorite : suspend(Favorite) ->Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items= routes){
            item ->
            FavoriteRouteCard(
                route = item,
                coroutineScope = coroutineScope,
                removeFromFavorite = removeFromFavorite
            )
        }
    }
}

@Composable
fun FavoriteRouteCard(
    route: FavoriteAndAirports,
    coroutineScope: CoroutineScope,
    removeFromFavorite : suspend(Favorite) ->Unit,
    modifier: Modifier = Modifier
){

    Card(
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Column {
                Text(
                    text = "DEPART",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
                AirportInfoRow(airport = route.departureAirport, modifier = Modifier.width(300.dp))
                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
                AirportInfoRow(airport = route.destinationAirport, modifier = Modifier.width(300.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        removeFromFavorite(route.favorite)
                    }
                }
//                        
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    tint = Color.Red ,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

    }
}