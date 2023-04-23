package com.example.tempoapp

enum class TempoDayColor {
    BLEU,
    BLANC,
    ROUGE,
    UNKNOWN
}

data class TempoDay(val date: String, val color: TempoDayColor)
