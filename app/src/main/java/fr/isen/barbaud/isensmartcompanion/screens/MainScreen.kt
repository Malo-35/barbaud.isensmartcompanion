package fr.isen.barbaud.isensmartcompanion.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
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
    var discussionList = remember { mutableStateOf<List<String?>>(emptyList()) }


    //val apiKey = BuildConfig.apiKey
    val ApiKey="AIzaSyBCRAYykFfKQNRooe0yK8QS5f3OGmX0qlM"
    val testAI = com.google.ai.client.generativeai.GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = ApiKey
    )
    Box(modifier = Modifier.fillMaxWidth()
        .fillMaxSize()
        .padding(innerPadding)) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(R.drawable.isen),
                context.getString(R.string.isen_logo))
            Text(context.getString(R.string.app_name))
            Spacer(Modifier.weight(1f))
            /*ScrollableTabRow(
                selectedTabIndex = 1
            ) {*/
            LazyColumn {
                items(discussionList.value) { eachEvent ->
                    Text(
                        "${eachEvent}",
                        modifier = Modifier
                            .padding(0.dp, 3.dp)
                    )
                }
            }
            Spacer(Modifier.height(100.dp))
        }
        Column {
            Spacer(Modifier.weight(1f))
            Row(modifier = Modifier
                .fillMaxWidth()
                .requiredHeightIn(90.dp,500.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                //.weight(1F)
            ) {
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

                OutlinedButton(onClick = {
                    Toast.makeText(context, "Question Submitted", Toast.LENGTH_LONG).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        var AIAnswer = testAI.generateContent(userInput.value)

                        Log.d("TUEZ-MOI !!!", "Valeur brute : ${AIAnswer.text}")

                        MyGodDamnAnswer.value = AIAnswer.text // Mise à jour de l'état de ma ****** de réponse...
                        discussionList.value = discussionList.value + MyGodDamnAnswer.value
                        userInput.value = ""
                    }
                },  modifier = Modifier
                    .background(Color.Red, shape = RoundedCornerShape(50)),
                    content = {
                        Image(painterResource(R.drawable.send), "")
                    })
            }
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