package com.idgs.examen2doparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.idgs.examen2doparcial.ui.theme.Examen2doParcialTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Examen2doParcialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "datos_personales") {
                    composable("datos_personales") {
                        DatosPersonalesScreen(
                            onCancelar = { finish() },
                            onSiguiente = { datos ->
                                val datosJson = Gson().toJson(datos)
                                navController.navigate("preguntas/$datosJson")
                            }
                        )
                    }
                    composable(
                        "preguntas/{datosJson}",
                        arguments = listOf(navArgument("datosJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val datosJson = backStackEntry.arguments?.getString("datosJson") ?: ""
                        val datos = Gson().fromJson(datosJson, DatosPersonales::class.java)
                        PreguntasScreen(
                            onVolver = { navController.popBackStack() },
                            onFinalizar = { calificacion, totalPreguntas ->
                                val datosJson2 = Gson().toJson(datos)
                                navController.navigate("resultados/$datosJson2/$calificacion/$totalPreguntas")
                            }
                        )
                    }

                    composable(
                        "resultados/{datosJson}/{calificacion}/{totalPreguntas}",
                        arguments = listOf(
                            navArgument("datosJson") { type = NavType.StringType },
                            navArgument("calificacion") { type = NavType.IntType },
                            navArgument("totalPreguntas") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val datosJson = backStackEntry.arguments?.getString("datosJson") ?: ""
                        val datos = Gson().fromJson(datosJson, DatosPersonales::class.java)
                        val calificacion = backStackEntry.arguments?.getInt("calificacion") ?: 0
                        val totalPreguntas = backStackEntry.arguments?.getInt("totalPreguntas") ?: 0
                        ResultadosScreen(
                            datos = datos,
                            calificacion = calificacion,
                            totalPreguntas = totalPreguntas,
                            onFinalizar = { finish() }
                        )
                    }
                }
            }
        }
    }
}