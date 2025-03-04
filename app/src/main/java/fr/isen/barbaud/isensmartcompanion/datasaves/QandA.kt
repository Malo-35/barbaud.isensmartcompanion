package fr.isen.barbaud.isensmartcompanion.datasaves

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class QandA(
    @PrimaryKey(true) val uid: Int = 0,
    @ColumnInfo(name = "Question") val question: String,
    @ColumnInfo(name = "Réponse") val answer: String,
    @ColumnInfo(name = "Date") val date: String,
)
