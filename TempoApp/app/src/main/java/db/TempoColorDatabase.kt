package com.example.tempo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tempo.data.model.Tempo

/**
 * Room database for Tempo app.
 */
@Database(entities = [Tempo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TempoDb : RoomDatabase() {

    abstract fun tempoDao(): TempoDao
}
