package com.example.flightsearch.ui.screens



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.MockData
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchSuggestionScreen(
    suggestions : List<Airport>,
    onClick : (Airport)->Unit,
    modifier: Modifier = Modifier
){
    if (suggestions.isEmpty()){
        Text(
            text = "No results",
            textAlign = TextAlign.Left,
            modifier = modifier
                .height(100.dp)
                .padding(8.dp)
        )
    }
    else{
        SearchSuggestionList(
            suggestions = suggestions,
            onClick = onClick,
            modifier = modifier
//                .padding(8.dp)
        )
    }
}

@Composable
fun SearchSuggestionList(
    suggestions : List<Airport>,
    onClick : (Airport)->Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(0.dp)
    ) {
        items(items = suggestions)
        { item ->
            SuggestionCard(suggestion = item, onClick = onClick, modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionCard(
    suggestion : Airport,
    onClick : (Airport)->Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = {onClick(suggestion)},
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .padding(0.dp)
            .height(28.dp)
    ) {
        AirportInfoRow(airport = suggestion)
    }
}

@Composable
fun AirportInfoRow(
    airport: Airport,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(4.dp)

    ){
        Text(text = airport.code, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = airport.name, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}

@Composable
fun AirportInfoRow(
    code: String,
    name: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(4.dp)
    ){
        Text(text = code, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchSuggestionListPreview() {
    FlightSearchTheme {
        SearchSuggestionList(
            suggestions = MockData.mockAirportList,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportInfoRowPreview() {
    FlightSearchTheme {
        AirportInfoRow(airport = Airport(0, "Sheremetyevo - A.S. Pushkin international airport", "SVO", 100))
    }
}
