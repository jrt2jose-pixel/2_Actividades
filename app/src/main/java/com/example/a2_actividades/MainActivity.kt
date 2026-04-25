package com.example.a2_actividades

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2_actividades.ui.theme._2actividadesTheme


class MainActivity : ComponentActivity() {

    // ✅ BUENA PRÁCTICA #2: companion object con const val para claves de Intent
    // Previene errores tipográficos al reutilizar la clave en cualquier parte del proyecto
    companion object {
        const val EXTRA_ORIGIN = "com.equipo1.a2__actividades.EXTRA_ORIGIN"
        const val ORIGIN_VALUE = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ✅ BUENA PRÁCTICA #3: El tema centraliza colores y tipografía (Material 3)
            _2actividadesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // ✅ BUENA PRÁCTICA #1: La Activity solo orquesta; la UI vive en el Composable
                    ActivityOneScreen(
                        modifier = Modifier.padding(innerPadding),
                        onGoToMenu = {
                            // ✅ BUENA PRÁCTICA #4: Intent Explícito con clase destino concreta
                            val intent = Intent(this, SecondActivity::class.java).apply {
                                // Usamos la constante definida arriba — nunca un String literal
                                putExtra(EXTRA_ORIGIN, ORIGIN_VALUE)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// ✅ BUENA PRÁCTICA #1: Composable con responsabilidad única
//    Solo describe la UI de bienvenida. No conoce nada de Intents.
// ─────────────────────────────────────────────
@Composable
fun ActivityOneScreen(
    onGoToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // ✅ Color desde el tema, nunca hardcodeado
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // — Ícono decorativo del restaurante —
            Icon(
                imageVector = Icons.Filled.Restaurant,
                contentDescription = stringResource(R.string.cd_restaurant_logo),
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary // ✅ Color desde el tema
            )

            Spacer(modifier = Modifier.height(24.dp))

            // — Nombre del restaurante —
            // ✅ BUENA PRÁCTICA #3: Texto desde stringResource, tipografía desde MaterialTheme
            Text(
                text = stringResource(R.string.restaurant_name),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // — Separador visual sutil —
            Text(
                text = "──────────",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(8.dp))

            // — Slogan —
            Text(
                text = stringResource(R.string.restaurant_slogan),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                letterSpacing = androidx.compose.ui.unit.TextUnit(
                    2f,
                    androidx.compose.ui.unit.TextUnitType.Sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // — Tagline descriptivo —
            Text(
                text = stringResource(R.string.restaurant_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // — Botón de navegación principal —
            Button(
                onClick = onGoToMenu,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .width(220.dp)
                    .height(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Restaurant,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // ✅ BUENA PRÁCTICA #3: Texto del botón desde stringResource
                Text(
                    text = stringResource(R.string.btn_ver_menu),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// Preview — solo para Android Studio, no afecta producción
// ─────────────────────────────────────────────
@Preview(showBackground = true, name = "Modo Claro")
@Composable
fun WelcomeScreenPreviewLight() {
    _2actividadesTheme(darkTheme = false) {
        ActivityOneScreen(onGoToMenu = {})
    }
}

@Preview(showBackground = true, name = "Modo Oscuro")
@Composable
fun WelcomeScreenPreviewDark() {
    _2actividadesTheme(darkTheme = true) {
        ActivityOneScreen(onGoToMenu = {})
    }
}