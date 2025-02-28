package fr.isen.barbaud.isensmartcompanion.models

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.GenerateContentResponse
import com.google.firebase.vertexai.vertexAI

@SuppressLint("NotConstructor")
class AnsweringAI(userInput: MutableState<String>) {
    suspend fun AnswerPlease(userInput: MutableState<String>): GenerateContentResponse {
        this.generativeModel = Firebase.vertexAI.generativeModel("gemini-2.0-flash")
        this.response = generativeModel!!.generateContent(userInput.value)
        return response as GenerateContentResponse
    }
    fun repeatAnswer(): GenerateContentResponse?{
        return response
    }
    var generativeModel:GenerativeModel? = null
    public var response: GenerateContentResponse? = null
}