package com.example.tempo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tempo.data.model.Tempo
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for Tempo entity.
 */
@Dao
interface TempoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tempo: Tempo)

    @Query("SELECT * FROM tempo")
    fun getAllTempos(): Flow<List<Tempo>>

    @Query("SELECT * FROM tempo WHERE date = :date")
    suspend fun getTempoByDate(date: String): Tempo?
}
