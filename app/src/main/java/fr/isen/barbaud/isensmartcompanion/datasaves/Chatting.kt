package fr.isen.barbaud.isensmartcompanion.datasaves

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Chatting {
    @Query("SELECT * FROM qanda ORDER BY qanda.date DESC")
    fun getAll(): List<QandA>

    @Delete
    fun delete(qandA: QandA)

    @Insert
    fun post(qandA: QandA)
}