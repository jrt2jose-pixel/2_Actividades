package com.example.a2_actividades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2_actividades.ui.theme._2actividadesTheme

// ─────────────────────────────────────────────
// Modelo de datos local — Responsabilidad Única:
// Representa un platillo del menú. Simple y sin lógica.
// ─────────────────────────────────────────────
data class MenuItem(
    val nameRes: Int,       // ID del string resource del nombre
    val descRes: Int,       // ID del string resource de la descripción
    val priceRes: Int,      // ID del string resource del precio
    val icon: ImageVector   // Ícono representativo
)

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Leemos el extra enviado desde MainActivity usando la constante segura
        // ✅ BUENA PRÁCTICA #2: Usamos la clave definida en el companion object de MainActivity
        val origin = intent.getStringExtra(MainActivity.EXTRA_ORIGIN) ?: "Desconocido"

        setContent {
            // ✅ BUENA PRÁCTICA #3: Tema centralizado Material 3
            _2actividadesTheme {
                // ✅ BUENA PRÁCTICA #1: La Activity solo orquesta; la UI vive en el Composable
                ActivityTwoScreen(
                    origin = origin,
                    onBackToActivityOne = {
                        // ✅ BUENA PRÁCTICA #4: finish() libera esta Activity y regresa al back stack
                        finish()
                    }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// ✅ BUENA PRÁCTICA #1: Composable con responsabilidad única
//    Solo describe la UI del menú. No conoce Intents ni lógica de negocio.
// ─────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityTwoScreen(
    onBackToActivityOne: () -> Unit,
    origin: String = "",
    modifier: Modifier = Modifier
) {
    // Lista de platillos definida localmente — escalable a ViewModel en proyectos reales
    val platillosPrincipales = listOf(
        MenuItem(
            nameRes  = R.string.dish_1_name,
            descRes  = R.string.dish_1_desc,
            priceRes = R.string.dish_1_price,
            icon     = Icons.Filled.Restaurant
        ),
        MenuItem(
            nameRes  = R.string.dish_2_name,
            descRes  = R.string.dish_2_desc,
            priceRes = R.string.dish_2_price,
            icon     = Icons.Filled.Restaurant
        ),
        MenuItem(
            nameRes  = R.string.dish_3_name,
            descRes  = R.string.dish_3_desc,
            priceRes = R.string.dish_3_price,
            icon     = Icons.Filled.Restaurant
        )
    )

    val bebidas = listOf(
        MenuItem(
            nameRes  = R.string.dish_4_name,
            descRes  = R.string.dish_4_desc,
            priceRes = R.string.dish_4_price,
            icon     = Icons.Filled.EmojiFoodBeverage
        )
    )

    Scaffold(
        // ✅ TopAppBar centrado — Material 3 con colores del tema
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                navigationIcon = {
                    // ✅ BUENA PRÁCTICA #4: El botón de regreso ejecuta finish()
                    IconButton(onClick = onBackToActivityOne) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.btn_regresar),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()), // Scroll por si el menú crece
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // — Subtítulo elegante —
                Text(
                    text = stringResource(R.string.menu_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(
                        3f,
                        androidx.compose.ui.unit.TextUnitType.Sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // — Sección: Platos Principales —
                MenuSectionHeader(
                    title = stringResource(R.string.menu_category_principales)
                )

                Spacer(modifier = Modifier.height(12.dp))

                platillosPrincipales.forEach { platillo ->
                    MenuItemCard(item = platillo)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // — Sección: Bebidas —
                MenuSectionHeader(
                    title = stringResource(R.string.menu_category_bebidas)
                )

                Spacer(modifier = Modifier.height(12.dp))

                bebidas.forEach { bebida ->
                    MenuItemCard(item = bebida)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ─────────────────────────────────────────────
// ✅ BUENA PRÁCTICA #1: Composable con responsabilidad única
//    Solo renderiza el encabezado de una sección del menú
// ─────────────────────────────────────────────
@Composable
fun MenuSectionHeader(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = androidx.compose.ui.unit.TextUnit(
                2f,
                androidx.compose.ui.unit.TextUnitType.Sp
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            thickness = 0.8.dp
        )
    }
}

// ─────────────────────────────────────────────
// ✅ BUENA PRÁCTICA #1: Composable con responsabilidad única
//    Solo renderiza una Card de platillo. No sabe nada del resto de la UI.
// ─────────────────────────────────────────────
@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            // ✅ BUENA PRÁCTICA #3: Colores desde MaterialTheme, nunca hardcodeados
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Minimalista: sin sombra
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // — Ícono del platillo —
            Icon(
                imageVector = item.icon,
                contentDescription = stringResource(R.string.cd_dish_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            // — Nombre y descripción (ocupa el espacio disponible con weight) —
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    // ✅ BUENA PRÁCTICA #3: Texto desde stringResource
                    text = stringResource(item.nameRes),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(item.descRes),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // — Precio alineado a la derecha —
            Text(
                text = stringResource(item.priceRes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )
        }
    }
}

// ─────────────────────────────────────────────
// Previews duales — Modo claro y oscuro
// ─────────────────────────────────────────────
@Preview(showBackground = true, name = "Menú — Modo Claro")
@Composable
fun MenuScreenPreviewLight() {
    _2actividadesTheme(darkTheme = false) {
        ActivityTwoScreen(onBackToActivityOne = {})
    }
}

@Preview(showBackground = true, name = "Menú — Modo Oscuro")
@Composable
fun MenuScreenPreviewDark() {
    _2actividadesTheme(darkTheme = true) {
        ActivityTwoScreen(onBackToActivityOne = {})
    }
}