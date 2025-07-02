package com.idgs.examen2doparcial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

fun guardarDatosEnFirebase(datos: DatosPersonales, calificacion: Int) {
    val db = FirebaseFirestore.getInstance()
    val registro = hashMapOf(
        "nombre" to datos.nombre,
        "apellidoPaterno" to datos.apellidoPaterno,
        "apellidoMaterno" to datos.apellidoMaterno,
        "dia" to datos.dia,
        "mes" to datos.mes,
        "anio" to datos.anio,
        "sexo" to datos.sexo,
        "calificacion" to calificacion
    )
    db.collection("resultados")
        .add(registro)
}

@Composable
fun ResultadosScreen(
    datos: DatosPersonales,
    calificacion: Int,
    totalPreguntas: Int,
    onFinalizar: () -> Unit
) {
    // Cálculo de edad
    val birthYear = datos.anio.toIntOrNull() ?: 2000
    val birthMonth = datos.mes.toIntOrNull() ?: 1
    val birthDay = datos.dia.toIntOrNull() ?: 1
    val today = LocalDate.now()
    var edad = today.year - birthYear
    if (today.monthValue < birthMonth || (today.monthValue == birthMonth && today.dayOfMonth < birthDay)) {
        edad -= 1
    }

    // Zodiaco chino
    val animalesZodiaco = listOf(
        "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
        "Caballo", "Cabra", "Mono", "Gallo", "Perro", "Cerdo"
    )
    val signoIndex = if (datos.anio.length == 4) (birthYear - 4) % 12 else 0
    val signoZodiaco = animalesZodiaco[if (signoIndex >= 0) signoIndex else (signoIndex + 12) % 12]

    // Imagen del signo
    val context = LocalContext.current
    val imageName = signoZodiaco.lowercase()
    val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    // Guardar en Firebase una sola vez
    LaunchedEffect(Unit) {
        guardarDatosEnFirebase(datos, calificacion)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hola ${datos.nombre}", fontSize = 28.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "Tienes $edad años y tu signo zodiacal es:", fontSize = 20.sp)
        if (imageId != 0) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = signoZodiaco,
                modifier = Modifier.size(120.dp)
            )
        }
        Text(text = signoZodiaco, fontSize = 32.sp, modifier = Modifier.padding(vertical = 16.dp))
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "Calificación del examen:",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "$calificacion / $totalPreguntas",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onFinalizar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar")
        }
    }
}