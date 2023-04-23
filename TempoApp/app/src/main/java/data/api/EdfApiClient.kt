package com.example.tempoapp.api

import com.example.tempoapp.TempoDay
import com.example.tempoapp.TempoDayColor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EdfApiClient {
    private val baseUrl = "https://particulier.edf.fr/services/rest/referentiel/"

    fun getNbTempoDays(): Map<TempoDayColor, Int> {
        val url = "${baseUrl}getNbTempoDays"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw Exception("Unexpected code $response")

        val json = JSONObject(response.body!!.string())
        val nbBlanc = json.getInt("PARAM_NB_J_BLANC")
        val nbRouge = json.getInt("PARAM_NB_J_ROUGE")
        val nbBleu = json.getInt("PARAM_NB_J_BLEU")

        return mapOf(
            TempoDayColor.BLANC to nbBlanc,
            TempoDayColor.ROUGE to nbRouge,
            TempoDayColor.BLEU to nbBleu
        )
    }

    fun getTempoDays(dateBegin: Date, dateEnd: Date): List<TempoDay> {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val url = "${baseUrl}historicTEMPOStore?dateBegin=${formatter.format(dateBegin)}&dateEnd=${formatter.format(dateEnd)}"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw Exception("Unexpected code $response")

        val json = JSONObject(response.body!!.string()).getJSONObject("dates")
        val tempoDays = mutableListOf<TempoDay>()

        val iterator = json.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val item = json.getJSONObject(key)
            val date = item.getString("date")
            val colorStr = item.getString("couleur")
            val color = when (colorStr) {
                "TEMPO_BLEU" -> TempoDayColor.BLEU
                "TEMPO_BLANC" -> TempoDayColor.BLANC
                "TEMPO_ROUGE" -> TempoDayColor.ROUGE
                else -> TempoDayColor.UNKNOWN
            }
            tempoDays.add(TempoDay(date, color))
        }

        return tempoDays
    }
}