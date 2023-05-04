package com.example.drawerpessionerivas.db
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TempoEntity::class], version = 1, exportSchema = false)
abstract class TempoDatabase : RoomDatabase(){
    abstract fun tempoDao(): TempoDao

    companion object{
        @Volatile
        private var INSTANCE: TempoDatabase? = null

        fun getDatabase(
            context: Context
        ): TempoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TempoDatabase::class.java,
                    "tempo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}