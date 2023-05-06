package com.example.drawerpessionerivas

import com.example.drawerpessionerivas.model.ColorOfTheDay
import com.example.drawerpessionerivas.model.DateColor
import com.example.drawerpessionerivas.model.DateColorResponse
import com.example.drawerpessionerivas.model.NbColors
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tempoApi{
    @GET("historicTEMPOStore")
    fun getDates(
        @Query("dateBegin") dateBegin: Int,
        @Query("dateEnd") dateEnd: Int,
    ): Call<DateColorResponse>

    @GET("getNbTempoDays")
    fun getNbColors(): Call<NbColors>

    @GET("searchTempoStore")
    fun getTodayColor(
        @Query("dateRelevant") dateRelevant: String,
    ): Call<ColorOfTheDay>
}