package com.example.a2_actividades.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
// --- Esquema OSCURO (experiencia principal recomendada) ---
private val DarkColorScheme = darkColorScheme(
    primary          = Cacao80,
    onPrimary        = Color(0xFF3E1F00),
    primaryContainer = Color(0xFF5A2E00),
    onPrimaryContainer = Color(0xFFFFDCBC),

    secondary        = Tierra80,
    onSecondary      = Color(0xFF2E1A0E),
    secondaryContainer = Color(0xFF4A2E1E),
    onSecondaryContainer = Color(0xFFFFDCC4),

    tertiary         = Selva80,
    onTertiary       = Color(0xFF1A3019),

    background       = FondoOscuro,
    onBackground     = TextoOscuro,
    surface          = SuperfOscura,
    onSurface        = TextoOscuro,
    surfaceVariant   = Color(0xFF3D2C22),
    onSurfaceVariant = Color(0xFFCFB8A8),
    outline          = Color(0xFF7A6558)
)

// --- Esquema CLARO ---
private val LightColorScheme = lightColorScheme(
    primary          = Cacao40,
    onPrimary        = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDCBC),
    onPrimaryContainer = Color(0xFF2B1200),

    secondary        = Tierra40,
    onSecondary      = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDCC4),
    onSecondaryContainer = Color(0xFF1E0A00),

    tertiary         = Selva40,
    onTertiary       = Color(0xFFFFFFFF),

    background       = FondoClaro,
    onBackground     = TextoClaro,
    surface          = SuperfClara,
    onSurface        = TextoClaro,
    surfaceVariant   = Color(0xFFEFDDD0),
    onSurfaceVariant = Color(0xFF52433B),
    outline          = Color(0xFF85736A)
)

@Composable
fun _2actividadesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Desactivado para preservar la paleta personalizada
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography, // Usa el Typography.kt generado por defecto
        content     = content
    )
}