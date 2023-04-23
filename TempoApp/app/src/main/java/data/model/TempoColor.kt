enum class TempoColor(val hexCode: String) {
    BLANC("#FFFFFF"),
    BLEU("#00AEEF"),
    ROUGE("#FF3C00");

    companion object {
        fun fromString(color: String): TempoColor {
            return when (color) {
                "TEMPO_BLANC" -> BLANC
                "TEMPO_BLEU" -> BLEU
                "TEMPO_ROUGE" -> ROUGE
                else -> throw IllegalArgumentException("Invalid Tempo color: $color")
            }
        }
    }
}
