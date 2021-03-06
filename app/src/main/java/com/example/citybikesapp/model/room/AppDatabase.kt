package com.example.citybikesapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.citybikesapp.model.entity.SavedCity
import com.example.citybikesapp.model.entity.SearchResult

@Database(entities = [SavedCity::class, SearchResult::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun searchDao(): SearchDao
    abstract fun savedDao(): SavedDao
    
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            var tempInstance = instance

            if(tempInstance == null){
                synchronized(this){
                    var newInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "CityBikesAppDB"
                    ).fallbackToDestructiveMigration().build()
                    instance = newInstance
                    return newInstance
                }
            } else return tempInstance
        }
    }
}