package com.idgs.examen2doparcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DatosPersonalesScreen(
    viewModel: DatosPersonalesViewModel = viewModel(),
    onCancelar: () -> Unit,
    onSiguiente: (DatosPersonales) -> Unit
) {
    val nombre by viewModel.nombre
    val apellidoPaterno by viewModel.apellidoPaterno
    val apellidoMaterno by viewModel.apellidoMaterno
    val dia by viewModel.dia
    val mes by viewModel.mes
    val anio by viewModel.anio
    val sexo by viewModel.sexo

    var mostrarError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Datos Personales",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.updateNombre(it) },
                label = { Text("Nombre(s)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellidoPaterno,
                onValueChange = { viewModel.updateApellidoPaterno(it) },
                label = { Text("Apellido Paterno") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellidoMaterno,
                onValueChange = { viewModel.updateApellidoMaterno(it) },
                label = { Text("Apellido Materno") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = dia,
                    onValueChange = { if (it.length <= 2) viewModel.updateDia(it) },
                    label = { Text("Día") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = mes,
                    onValueChange = { if (it.length <= 2) viewModel.updateMes(it) },
                    label = { Text("Mes") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = anio,
                    onValueChange = { if (it.length <= 4) viewModel.updateAnio(it) },
                    label = { Text("Año") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sexo: ", modifier = Modifier.padding(end = 8.dp))

                RadioButton(
                    selected = sexo == "Femenino",
                    onClick = { viewModel.updateSexo("Femenino") }
                )
                Text("Femenino", modifier = Modifier.padding(end = 16.dp))

                RadioButton(
                    selected = sexo == "Masculino",
                    onClick = { viewModel.updateSexo("Masculino") }
                )
                Text("Masculino")
            }

            if (mostrarError) {
                Text(
                    text = "Por favor, completa todos los campos obligatorios",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        if (nombre.isNotBlank() && apellidoPaterno.isNotBlank() && dia.isNotBlank() && mes.isNotBlank() && anio.isNotBlank()) {
                            onSiguiente(
                                DatosPersonales(
                                    nombre,
                                    apellidoPaterno,
                                    apellidoMaterno,
                                    dia,
                                    mes,
                                    anio,
                                    sexo
                                )
                            )
                        } else {
                            mostrarError = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Siguiente")
                }
            }
        }
    }
}