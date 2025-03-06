package fr.isen.barbaud.isensmartcompanion.datasaves

import android.content.Context
import androidx.room.Room

object AppDatabaseSingleton {
    // Variable privée pour stocker l'instance unique
    @Volatile
    private var INSTANCE: AppDatabase? = null

    // Fonction pour obtenir l'instance unique
    fun getInstance(context: Context): AppDatabase {
        // Double-check locking pour thread-safe
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database-name"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
