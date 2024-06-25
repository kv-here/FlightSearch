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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportWithFavoriteCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchResultsScreen(
    from: Airport,
    results: List<AirportWithFavoriteCheck>,
    addToFavorite: suspend(String, String) ->Unit,
    removeFromFavorite: suspend(String, String) ->Unit,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            text = "Flights from ${from.code}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(8.dp)
        )
        ResultsList(
            from = from,
            to = results,
            addToFavorite = addToFavorite,
            removeFromFavorite = removeFromFavorite,
            coroutineScope = coroutineScope,
        )

    }
}

@Composable
fun ResultsList(
    from : Airport,
    to : List<AirportWithFavoriteCheck>,
    addToFavorite : suspend(String, String) ->Unit,
    removeFromFavorite : suspend(String, String) ->Unit,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(items = to){
            item ->
                RouteCard(
                    from = from,
                    to = item,
                    coroutineScope = coroutineScope,
                    addToFavorite = addToFavorite,
                    removeFromFavorite = removeFromFavorite,
                )
        }
    }
}

@Composable
fun RouteCard(
    from: Airport,
    to : AirportWithFavoriteCheck,
    coroutineScope: CoroutineScope,
    addToFavorite : suspend(String, String) ->Unit,
    removeFromFavorite : suspend(String, String) ->Unit,
    modifier: Modifier = Modifier
){
    var isFav by remember { mutableStateOf(to.isFavorite) }
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
                AirportInfoRow(airport = from, modifier = Modifier.width(300.dp))
                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
                AirportInfoRow(code = to.code, name = to.name, modifier = Modifier.width(300.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                    coroutineScope.launch{
                        withContext(Dispatchers.IO){
                        if(isFav){
                            removeFromFavorite(from.code, to.code)
                        }
                        else{
                                addToFavorite(from.code, to.code)
                        }
                        isFav = !isFav
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    tint = if (isFav) Color.Red else Color.Gray,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun RouteCardPreview() {
//    FlightSearchTheme {
//        RouteCard(
//            from = MockData.mockAirportList[0],
//            to = MockData.mockAirportList[1],
//            isFavourite = false,
//            onClick = {}
//        )
//    }
//}
