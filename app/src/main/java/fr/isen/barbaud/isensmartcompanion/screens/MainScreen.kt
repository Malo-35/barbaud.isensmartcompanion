package fr.isen.barbaud.isensmartcompanion.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.BuildConfig
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.GenerateContentResponse
import fr.isen.barbaud.isensmartcompanion.R
import fr.isen.barbaud.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import fr.isen.barbaud.isensmartcompanion.models.AnsweringAI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("") }
    var MyGodDamnAnswer = remember { mutableStateOf<String?>("") }


    //val apiKey = BuildConfig.apiKey
    val ApiKey="AIzaSyBCRAYykFfKQNRooe0yK8QS5f3OGmX0qlM"
    val testAI = com.google.ai.client.generativeai.GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = ApiKey
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painterResource(R.drawable.isen),
            context.getString(R.string.isen_logo))
        Text(context.getString(R.string.app_name))
        Text("", modifier = Modifier
            .fillMaxSize()
            .weight(0.5F))
        Text("Couycou : ${MyGodDamnAnswer.value}")
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)) {
            TextField(
                value = userInput.value,
                onValueChange = { newValue ->
                    userInput.value = newValue
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent),
                modifier = Modifier.weight(1F))
            /*OutlinedButton ( onClick = {
                Toast.makeText(context, "Question Submitted", Toast.LENGTH_LONG).show()
                var testclass = AnsweringAI(userInput)
                CoroutineScope(Dispatchers.IO).launch{
                    var AIAnswer = testAI.generateContent(userInput.value)
                    var testcheck = mutableStateOf(AIAnswer.text)

                    MyGodDamnAnswer = (mutableStateOf(AIAnswer.text))
                    Log.d("TUEZ-MOI !!!", "Valeure brute : ${MyGodDamnAnswer}")
                    Log.d("TUEZ-MOI !!! ENCORE", "Valeure value : ${MyGodDamnAnswer.value}")
                    MyGodDamnAnswer.value = MyGodDamnAnswer.value
                }*/
            OutlinedButton(onClick = {
                Toast.makeText(context, "Question Submitted", Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.IO).launch {
                    var AIAnswer = testAI.generateContent(userInput.value)

                    Log.d("TUEZ-MOI !!!", "Valeur brute : ${AIAnswer.text}")

                    MyGodDamnAnswer.value = AIAnswer.text // ✅ Mise à jour correcte de l'état
                }
        },  modifier = Modifier
                .background(Color.Red, shape = RoundedCornerShape(50)),
                content = {
                    Image(painterResource(R.drawable.send), "")
                })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ISENSmartCompanionTheme {
        MainScreen(PaddingValues(8.dp))
    }
}