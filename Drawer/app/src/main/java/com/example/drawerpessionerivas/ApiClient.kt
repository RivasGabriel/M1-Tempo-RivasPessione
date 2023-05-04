package com.example.drawerpessionerivas

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// api : https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2022&dateEnd=2023
// data : dates : { 0:{ date:"2022-5-4", couleur:"TEMPO_BLEU"}

// api : https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays
// data : { "PARAM_NB_J_BLANC": 43, "PARAM_NB_J_BLEU": 43, "PARAM_NB_J_ROUGE": 43,}
class ApiClient {
    companion object {
        val TEMPO_DATE_BASE_URL = "https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore"
        val TEMPO_NB_BASE_URL = "https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays"

        val instance = Build()

        private fun Build(): Retrofit {
            val converter = GsonConverterFactory.create()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(TEMPO_DATE_BASE_URL)
                .addConverterFactory(converter)
                .client(okHttpClient)
                .build()
        }
    }

}