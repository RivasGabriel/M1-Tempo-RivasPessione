package com.example.drawerpessionerivas.model;

// api : https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2022&dateEnd=2023
// data : dates : { 0:{ date:"2022-5-4", couleur:"TEMPO_BLEU"}


import com.google.gson.annotations.SerializedName;

data class DateColor(
    @SerializedName("date") var date: String? = null,
    @SerializedName("couleur") var color: String? = null
)