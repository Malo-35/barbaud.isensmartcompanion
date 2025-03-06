package fr.isen.barbaud.isensmartcompanion.datasaves

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QandA::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ChattingDao(): Chatting
}