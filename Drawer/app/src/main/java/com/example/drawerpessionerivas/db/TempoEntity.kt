package com.example.drawerpessionerivas.db
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
// api : https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2022&dateEnd=2023
// data : dates : { 0:{ date:"2022-5-4", couleur:"TEMPO_BLEU"}

// api : https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays
// data : { "PARAM_NB_J_BLANC": 43, "PARAM_NB_J_BLEU": 43, "PARAM_NB_J_ROUGE": 43,}
@Entity(tableName = "tempo_table_date")
data class TempoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "couleur") val couleur: String
)

@Entity(tableName = "tempo_table_nb")
data class TempoNbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "PARAM_NB_J_BLANC") val blanc: Int,
    @ColumnInfo(name = "PARAM_NB_J_BLEU") val bleu: Int,
    @ColumnInfo(name = "PARAM_NB_J_ROUGE") val rouge: Int
)