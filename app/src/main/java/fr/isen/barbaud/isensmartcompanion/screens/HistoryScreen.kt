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

//Je me suis aidé de Chat GPT pour réparer beaucoup de mes erreures mais le procédé reste preuve d'une réflexion personnelle.
/*Je procède en appelant la BDD que j'ai déjà initialisé en amond dans d'autres fichiers.
Donc, disais-je : Je prend ma BDD, je stock le retour de la fonction getAll() dans ma variable "myvalues", elle est en remember car je veux que la page s'actualise à chaque suppression d'intéraction.
J'utilise ensuite un "LazyColumn" avec un "items()" afin que ma liste "myvalues" soit découpée et générer "automatiquement" (vu le nombre de galères rencontré durant le développement de ce code je ne dirai jamais que quelque chose est automatique !) chaque intéractions contenues dans la BDD.
Initialement je voulais passer directement par la fonction getAll comme ceci : items(MyDataBase.getAll())
Méthode assez barbare je le reconnais mais si ça marche... ça marche.
En l'occurence, non, ça ne marchait pas car le programme coupait le processus et hurlait que ce n'était pas possible de le faire dans le main thread car cela *pouvait* bloquer le programme... Passer des heures à changer un programme car il fait une crise de nerf sur un *peut-être* est une expérience que je ne souhaite à aucune personne... Ni à moi d'ailleurs !*/
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