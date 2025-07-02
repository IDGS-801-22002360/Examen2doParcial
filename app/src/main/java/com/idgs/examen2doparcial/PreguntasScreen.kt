package com.idgs.examen2doparcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreguntasScreen(
    onVolver: () -> Unit,
    onFinalizar: (calificacion: Int, totalPreguntas: Int) -> Unit
) {
    val preguntas = listOf(
        Pregunta(
            texto = "¿Cuánto es 7 + 5?",
            opciones = listOf("11", "12", "13", "14"),
            respuestaCorrecta = "12"
        ),
        Pregunta(
            texto = "¿Cuál es el resultado de 9 x 3?",
            opciones = listOf("27", "21", "18", "24"),
            respuestaCorrecta = "27"
        ),
        Pregunta(
            texto = "¿Quién escribió 'Cien años de soledad'?",
            opciones = listOf("Gabriel García Márquez", "Octavio Paz", "Mario Vargas Llosa", "Jorge Luis Borges"),
            respuestaCorrecta = "Gabriel García Márquez"
        ),
        Pregunta(
            texto = "¿Cuál es la raíz cuadrada de 81?",
            opciones = listOf("9", "8", "7", "6"),
            respuestaCorrecta = "9"
        ),
        Pregunta(
            texto = "¿Quién es el autor de 'Don Quijote de la Mancha'?",
            opciones = listOf("Miguel de Cervantes", "Lope de Vega", "Benito Pérez Galdós", "Federico García Lorca"),
            respuestaCorrecta = "Miguel de Cervantes"
        ),
        Pregunta(
            texto = "¿Cuánto es 15 dividido entre 3?",
            opciones = listOf("5", "4", "6", "3"),
            respuestaCorrecta = "5"
        )
    )

    val respuestas = remember { mutableStateMapOf<Int, String>() }
    var mostrarError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Cuestionario",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            preguntas.forEachIndexed { index, pregunta ->
                item {
                    Text(
                        text = pregunta.texto,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    pregunta.opciones.forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = respuestas[index] == opcion,
                                onClick = { respuestas[index] = opcion }
                            )
                            Text(opcion, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                if (mostrarError) {
                    Text(
                        text = "Por favor, responde todas las preguntas",
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onVolver,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Volver")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (respuestas.size == preguntas.size) {
                                val correctas = preguntas.indices.count { i ->
                                    respuestas[i] == preguntas[i].respuestaCorrecta
                                }
                                onFinalizar(correctas, preguntas.size)
                            } else {
                                mostrarError = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Finalizar")
                    }
                }
            }
        }
    }
}