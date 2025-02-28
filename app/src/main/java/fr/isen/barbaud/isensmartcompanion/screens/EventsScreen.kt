package fr.isen.barbaud.isensmartcompanion.screens


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.barbaud.isensmartcompanion.EventDetailActivity
import fr.isen.barbaud.isensmartcompanion.api.NetworkManager
import fr.isen.barbaud.isensmartcompanion.models.EventModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventsScreen(innerPadding: PaddingValues, eventHandler: (EventModel) -> Unit) {
    val context = LocalContext.current
    val events = remember{ mutableStateOf<List<EventModel>>(listOf()) }

    LaunchedEffect(Unit) {
        val call = NetworkManager.api.getEvents()
        call.enqueue(object: Callback<List<EventModel>> {
            var sucess:Int = 0

            override fun onResponse(p0: Call<List<EventModel>>, p1: Response<List<EventModel>>) {
                events.value = p1.body() ?: listOf()
            }
            override fun onFailure(p0: Call<List<EventModel>>, p1: Throwable) {
                Log.e("request",p1.message ?: "request failed") //Log.e -> e pour error, d pour debug
            }

        })
    }
    Column(modifier = Modifier.padding(innerPadding)) {
        //val events = EventModel.fakeEvents()
        LazyColumn {
            items(events.value) { eachEvent ->
                EventRow(eachEvent, eventHandler)
            }
        }
    }
}

@Composable fun EventRow(event: EventModel, eventHandler: (EventModel) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .clickable {
            eventHandler(event)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.title)
            Text(event.description)
        }
    }
}