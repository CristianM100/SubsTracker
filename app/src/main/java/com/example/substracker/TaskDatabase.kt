package com.example.substracker

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/* Declares a Room database with the Task entity (your table).
version = 2: DB version (used for migration if schema changes).
exportSchema = false: Don’t save the schema version into a folder (used during development to reduce clutter). */
@Database(entities = arrayOf(Task::class), version = 2, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() { // Inherits from RoomDatabase.
    abstract fun taskDao(): TaskDao // You must define at least one abstract method that returns your DAO (taskDao())

    /* A singleton ensures only one instance of the database is created (recommended by Room).
    @Volatile: Guarantees that INSTANCE changes are visible across threads. */
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // If the database hasn't been created yet, this will build it.
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Prevents memory leaks.
                    TaskDatabase::class.java,
                    "task_database" // The name of your .db file on the device.
                )
                    .fallbackToDestructiveMigration()
                    .build() /*  Deletes and recreates the database if you change the schema without providing a migration.
                    (Great for dev, not ideal for production.)*/

                // set the instance
                INSTANCE = instance
                instance
            }
        }
    }
}
