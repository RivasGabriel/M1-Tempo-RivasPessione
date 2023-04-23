interface TempoApi {
    // https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2022&dateEnd=2023
    @GET("historicTEMPOStore")
    suspend fun getTempoDays(@Query("dateBegin") dateBegin: String, @Query("dateEnd") dateEnd: String): List<TempoDay>

    //https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays
    @GET("getNbTempoDays")
    suspend fun getNbTempoDays(): Map<TempoDayColor, Int>

}