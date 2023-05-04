package com.example.drawerpessionerivas.db

import androidx.room.Dao
import androidx.room.Query

// api : https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2022&dateEnd=2023
// data : dates : { 0:{ date:"2022-5-4", couleur:"TEMPO_BLEU"}

// api : https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays
// data : { "PARAM_NB_J_BLANC": 43, "PARAM_NB_J_BLEU": 43, "PARAM_NB_J_ROUGE": 43,}

@Dao
interface TempoDao {
    @Query("SELECT * FROM tempo_table_date")
    fun getAll(): List<TempoEntity>

    @Query("SELECT * FROM tempo_table_date WHERE date LIKE :date")
    fun findByDate(date: String): TempoEntity

    // find between 2 dates
    @Query("SELECT * FROM tempo_table_date WHERE date BETWEEN :date1 AND :date2")
    fun findBetweenDates(date1: String, date2: String): List<TempoEntity>
}

@Dao
interface TempoNbDao {
    @Query("SELECT * FROM tempo_table_nb")
    fun getAll(): List<TempoNbEntity>
}
