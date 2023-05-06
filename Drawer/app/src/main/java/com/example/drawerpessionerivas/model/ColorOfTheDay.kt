package com.example.drawerpessionerivas.model
//https://particulier.edf.fr/services/rest/referentiel/searchTempoStore?dateRelevant=2023-02-09&TypeAlerte=TEMPO

import com.google.gson.annotations.SerializedName;

data class ColorOfTheDay (
    @SerializedName("couleurJourJ") var couleurJourJ: String? = null,
    @SerializedName("couleurJourJ1") var couleurJourJ1: String? = null
)