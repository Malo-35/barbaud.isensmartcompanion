package fr.isen.barbaud.isensmartcompanion

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.barbaud.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import org.w3c.dom.Text

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
    var userInput = remember { mutableStateOf("test") }
    Column(                                 //Je crée une colonne pour centrer tous mes objets
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color(255,0,255)), //Je dit que la colonne fait toute la largeure du téléphone
        horizontalAlignment = Alignment.CenterHorizontally,  //Je demande d'alligner tous les objects contenu dans les accolades au centre.
        //verticalArrangement = Arrangement.Bottom

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
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.wrapContentSize()
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(255,0,0))
        ){
            TextField(
                userInput.value,
                onValueChange = { newValue -> userInput.value = newValue },
                modifier = Modifier.weight(1F),  //Ici on dit que le TextField a plus de poids que le boutton send, donc la zone de texte prend autant de place qu'elle peut/en a besoin mais ne rejette pas les autres éléments en dehors du téléphone.
            )
            OutlinedButton(onClick = {
                /*Toast.makeText(context,"show toast\n${userInput.value}", Toast.LENGTH_LONG).show()*/
                Toast.makeText(context,"Question Submitted", Toast.LENGTH_LONG).show()  //Le toast c'est une mini popup qui apparait temporairement en bas de l'écrant, généralement utilisé pour avertir et faire du debug.
            },
                modifier = Modifier.background(Color(0,255,0)),
                content = {Image(painterResource(R.drawable.send), "send")}
            )
            /*Button({
                Log.d("testing","button clicked")
            },
                content = {
                    Image(painterResource(R.drawable.send), "send")
                })*/
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