package com.example.paginationflowremote.Api.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paginationflowremote.Api.ResultRickAndMorty

@Database(entities = [ResultRickAndMorty::class, CharacterRemoteKeys::class], version = 3, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun CharacterDao(): CharacterDao

    abstract fun RemoteKeysDao(): CharacterRemoteKeysDao



    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "character")
                .fallbackToDestructiveMigration()
                .build()
    }
}