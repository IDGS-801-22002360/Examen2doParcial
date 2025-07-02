package com.idgs.examen2doparcial

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: String
)