package com.example.flightsearch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsearch.R
import com.example.flightsearch.ui.search.SearchSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(){
    Scaffold(
        topBar = { InventoryTopAppBar() }
    ) {
        SearchSpace(modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,

    )
}