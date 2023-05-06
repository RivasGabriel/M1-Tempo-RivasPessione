package com.example.drawerpessionerivas.model

// api : https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays
// data : { "PARAM_NB_J_BLANC": 43, "PARAM_NB_J_BLEU": 43, "PARAM_NB_J_ROUGE": 43,}

import com.google.gson.annotations.SerializedName

data class NbColors (
    @SerializedName("PARAM_NB_J_BLANC") var blanc: Int? = null,
    @SerializedName("PARAM_NB_J_BLEU") var bleu: Int? = null,
    @SerializedName("PARAM_NB_J_ROUGE") var rouge: Int? = null
)