package fr.isen.barbaud.isensmartcompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.barbaud.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current      //JSP ce que ça fait...
    var text by rememberSaveable { mutableStateOf("test") }
    Column(                                 //Je crée une colonne pour centrer tous mes objets
        modifier = Modifier.fillMaxWidth(), //Je dit que la colonne fait toute la largeure du téléphone
        horizontalAlignment = Alignment.CenterHorizontally  //Je demande d'alligner tous les objects contenu dans les accolades au centre.
    ){
        Image(painterResource(R.drawable.isen), "isen logo")
        Text(
            text = "Hello $name!",
            modifier = modifier     //Ici on récupère le modifier parent qui apporte des modificateurs tels que le padding, centrer du text, etc...
        )
        Text(
            text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH !!!",
            modifier = modifier
        )
        Row{
            TextField(text, {})
            Button({
                Log.d("testing","button clicked")
            },
                content = {
                    Image(painterResource(R.drawable.send), "send")
                })
        }
    }
}

/*@Composable
fun ChatZone(modifier: Modifier = Modifier){
    Column { TextField() }
}*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ISENSmartCompanionTheme {
        Greeting("Android2")
    }
}