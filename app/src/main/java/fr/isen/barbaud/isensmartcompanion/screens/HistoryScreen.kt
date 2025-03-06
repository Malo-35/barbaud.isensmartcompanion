package fr.isen.barbaud.isensmartcompanion.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.room.Room
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.isen.barbaud.isensmartcompanion.datasaves.AppDatabase
import fr.isen.barbaud.isensmartcompanion.datasaves.AppDatabaseSingleton

@Composable
fun HistoryScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    // Récupérer l'instance unique de la base de données
    val db = AppDatabaseSingleton.getInstance(context)
    val MyDataBase = db.ChattingDao()
    Log.d("Test HISTORY", "${MyDataBase.getAll()}")

//    Box(modifier = Modifier
//        .fillMaxWidth()){
//        LazyColumn {
//            items(MyDataBase.getAll()){ EachEvent ->
//                Text(
//                    EachEvent.question,
//                    modifier = Modifier
//                        .background(Color.Gray),
//                    Color.White
//                )
//                Text(
//                    EachEvent.answer,
//                    modifier = Modifier
//                        .background(Color.LightGray),
//                    Color.Black
//                )
//            }
//        }
//    }
}