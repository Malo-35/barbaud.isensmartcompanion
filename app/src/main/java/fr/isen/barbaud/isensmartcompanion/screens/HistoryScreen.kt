package fr.isen.barbaud.isensmartcompanion.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.room.Room
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.isen.barbaud.isensmartcompanion.datasaves.AppDatabase
import fr.isen.barbaud.isensmartcompanion.datasaves.AppDatabaseSingleton
import fr.isen.barbaud.isensmartcompanion.datasaves.Chatting
import fr.isen.barbaud.isensmartcompanion.datasaves.QandA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import fr.isen.barbaud.isensmartcompanion.R

/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HistoryScreen(innerPadding: PaddingValues, MyDataBase: Chatting) {
    val context = LocalContext.current
    Log.d("Test HISTORY", "${MyDataBase.getAll()}")
    var myvalues:List<QandA> = MyDataBase.getAll()

    Box(modifier = Modifier
        .fillMaxWidth()){
        LazyColumn {
            items(myvalues){ EachEvent ->
                Text(
                    EachEvent.question,
                    modifier = Modifier
                        .background(Color.Gray),
                    Color.White
                )
                Text(
                    EachEvent.answer,
                    modifier = Modifier
                        .background(Color.LightGray),
                    Color.Black
                )
            }
        }
    }
}

@Composable
fun ShowHistory(mybdd: Chatting){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()){
        LazyColumn {
            items(mybdd.getAll()){ EachEvent ->
                Text(
                    EachEvent.question,
                    modifier = Modifier
                        .background(Color.Gray),
                    Color.White
                )
                Text(
                    EachEvent.answer,
                    modifier = Modifier
                        .background(Color.LightGray),
                    Color.Black
                )
            }
        }
    }
}*/

@Composable
fun HistoryScreen(innerPadding: PaddingValues, MyDataBase: Chatting) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()  // Crée un scope pour les coroutines

    var myvalues by remember { mutableStateOf(emptyList<QandA>()) }

    // Charger les données en arrière-plan dès le début
    androidx.compose.runtime.LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val dataFromDb = MyDataBase.getAll()  // Appel en arrière-plan
            myvalues = dataFromDb  // Mettre à jour les données dans le thread principal
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(contentPadding = innerPadding) {
            items(myvalues) { EachEvent ->
                Text(EachEvent.date,
                    modifier = Modifier.background(Color.DarkGray))
                Text(
                    EachEvent.question,
                    modifier = Modifier
                        .background(Color.Gray),
                    color = Color.White
                )
                Text(
                    EachEvent.answer,
                    modifier = Modifier
                        .background(Color.LightGray),
                    color = Color.Black
                )
                OutlinedButton(onClick = {
                    Toast.makeText(context, "Intéraction supprimée.", Toast.LENGTH_LONG).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("SUPPRESSION", "Tentative de suppression...")
                        MyDataBase.delete(EachEvent)
                            coroutineScope.launch(Dispatchers.IO) {
                                val dataFromDb = MyDataBase.getAll()  // Appel en arrière-plan
                                myvalues = dataFromDb  // Mettre à jour les données dans le thread principal
                            }

                    }
                },
                    modifier = Modifier
                        .background(Color.Red, shape = RoundedCornerShape(50)),
                    content = {
                        Image(painterResource(R.drawable.poubelle), "Delete current interaction")
                    })
            }
        }
    }
}