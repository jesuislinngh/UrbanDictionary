package com.example.android.urbandictionary.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private const val  DATABASE_NAME = "urbandictionary_database"

@Database(entities = [DefinitionEntity::class], version = 1, exportSchema = false)
abstract class DataBaseDefinitions : RoomDatabase() {

    abstract fun daoDefinition(): DaoDefinitionItem

    companion object {

        private val TAG =  DataBaseDefinitions::class.java.canonicalName

        @Volatile
        private var INSTANCE: DataBaseDefinitions? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DataBaseDefinitions {

            if (INSTANCE != null) return INSTANCE as DataBaseDefinitions

            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseDefinitions::class.java,
                    DATABASE_NAME
                ).build()
            }
        }

    }

    private class DatabaseDefinitionCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch {
                    Log.d(TAG, "$DATABASE_NAME created")
                }
            }
        }
    }
}
