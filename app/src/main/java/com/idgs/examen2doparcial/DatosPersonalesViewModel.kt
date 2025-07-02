package com.idgs.examen2doparcial

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class DatosPersonalesViewModel : ViewModel() {
    private val _nombre = mutableStateOf("")
    val nombre: State<String> get() = _nombre

    private val _apellidoPaterno = mutableStateOf("")
    val apellidoPaterno: State<String> get() = _apellidoPaterno

    private val _apellidoMaterno = mutableStateOf("")
    val apellidoMaterno: State<String> get() = _apellidoMaterno

    private val _dia = mutableStateOf("")
    val dia: State<String> get() = _dia

    private val _mes = mutableStateOf("")
    val mes: State<String> get() = _mes

    private val _anio = mutableStateOf("")
    val anio: State<String> get() = _anio

    private val _sexo = mutableStateOf("Masculino")
    val sexo: State<String> get() = _sexo

    fun updateNombre(value: String) { _nombre.value = value }
    fun updateApellidoPaterno(value: String) { _apellidoPaterno.value = value }
    fun updateApellidoMaterno(value: String) { _apellidoMaterno.value = value }
    fun updateDia(value: String) { _dia.value = value }
    fun updateMes(value: String) { _mes.value = value }
    fun updateAnio(value: String) { _anio.value = value }
    fun updateSexo(value: String) { _sexo.value = value }
}