package fr.isen.barbaud.isensmartcompanion

import android.content.ClipDescription
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.barbaud.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import java.util.Date

data class MyEventItem(
    val id: Int,
    val title: String,
    val description: String,
    val date: Date,
    val location: String,
    val category: String
)

class EventModel{
    constructor(id: Int, title: String, description: String, date: Date, location: String, category: String){
//        this.id=id
//        this.title=title
//        this.description=description
//        this.date=date
//        this.location=location
//        this.category=category
        this.MyEventList= (listOf(MyEventItem(id, title, description, date, location, category)))
    }
    constructor(giveListHere: List<MyEventItem>){
        this.MyEventList=giveListHere
    }
//    val id: Int = 0
//    val title: String = ""
//    val description: String = ""
//    val date: Date
//    val location: String
//    val category: String
    var MyEventList: List<MyEventItem>

    fun GetListEvents(): List<MyEventItem>{
        return MyEventList
    }

    fun PostEvent(newEvent:MyEventItem){
        this.MyEventList += newEvent
    }
    fun PostEvent(id: Int, title: String, description: String, date: Date, location: String, category: String){
        this.MyEventList += MyEventItem(id, title, description, date, location, category)
    }
    fun DeleteById(id: Int){
        //TODO
    }
}


class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EventsScreen(innerPadding: PaddingValues, eventHandler: () -> Unit){
    val context = LocalContext.current
    var myListEvent:List<MyEventItem> = listOf(MyEventItem(0,"NDISEN", "Nuit de programmation", Date(), "Sud", "Developpement"), MyEventItem(1,"Erasmus", "Étudiants étrangers", Date(), "ISEN", "Apprentissage"), MyEventItem(2, "ISEN Avengers", "Un mail salé de vérité...", Date(), "Amphythéatre", "Mémorable", ))

    var MyFakeEvents = EventModel(myListEvent)

    LazyColumn {
        items(MyFakeEvents.GetListEvents()) { item ->
            Text(item.title)
            Text(item.description)
            Text(item.date.toString())
            Text(item.location)
            Text(item.category)
        }
    }
    Button(
        onClick = {
            eventHandler()
            //val intent = Intent(context, EventDetailActivity::class.java)
            //context.startActivity(intent)
            //Log.d("eventScreen","onClick")
        },
        content = {
            Text("Temporary button")
        }
    )
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ISENSmartCompanionTheme {
        Greeting2("Android")
    }
}