package com.example.tempo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.tempo.data.Tempo
import com.example.tempo.data.TempoColor
import com.example.tempo.data.TempoDate
import com.example.tempo.data.TempoRemainingDays
import com.example.tempo.network.EdfApiService
import com.example.tempo.network.asDatabaseModel
import com.example.tempo.util.getNextTempos
import com.example.tempo.util.getRemainingDays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TempoRepository(private val edfApiService: EdfApiService) {

    val tempo: LiveData<List<Tempo>> = Transformations.map(edfApiService.getTempo()) {
        it.asDatabaseModel()
    }

    suspend fun refreshTempo() {
        withContext(Dispatchers.IO) {
            val tempoColors = edfApiService.getTempoColor()
            val tempoDates = edfApiService.getTempoDate()
            val remainingDays = edfApiService.getRemainingDays()

            val tempoList = getNextTempos(tempoColors.asDatabaseModel(), tempoDates.asDatabaseModel())
            val remainingDaysObject = getRemainingDays(remainingDays)

            tempoList.forEach { tempo ->
                tempo.remainingDays = remainingDaysObject
                when (tempo.color) {
                    TempoColor.BLEU -> {
                        tempo.date = tempoDates.asDatabaseModel()[0]
                    }
                    TempoColor.BLANC, TempoColor.ROUGE -> {
                        tempo.date = tempoDates.asDatabaseModel()[1]
                    }
                }
            }
            tempoColors.insertAll()
            tempoDates.insertAll()
            remainingDaysObject.insert()

            tempoList.forEach {
                it.insert()
            }
        }
    }
}
