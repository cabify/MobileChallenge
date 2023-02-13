package com.cabify.demo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Material 3 color schemes
private val cabifyDarkColorScheme = darkColorScheme(
    primary = cabifyDarkPrimary,
    onPrimary = cabifyDarkOnPrimary,
    primaryContainer = cabifyDarkPrimaryContainer,
    onPrimaryContainer = cabifyDarkOnPrimaryContainer,
    inversePrimary = cabifyDarkPrimaryInverse,
    secondary = cabifyDarkSecondary,
    onSecondary = cabifyDarkOnSecondary,
    secondaryContainer = cabifyDarkSecondaryContainer,
    onSecondaryContainer = cabifyDarkOnSecondaryContainer,
    tertiary = cabifyDarkTertiary,
    onTertiary = cabifyDarkOnTertiary,
    tertiaryContainer = cabifyDarkTertiaryContainer,
    onTertiaryContainer = cabifyDarkOnTertiaryContainer,
    error = cabifyDarkError,
    onError = cabifyDarkOnError,
    errorContainer = cabifyDarkErrorContainer,
    onErrorContainer = cabifyDarkOnErrorContainer,
    background = cabifyDarkBackground,
    onBackground = cabifyDarkOnBackground,
    surface = cabifyDarkSurface,
    onSurface = cabifyDarkOnSurface,
    inverseSurface = cabifyDarkInverseSurface,
    inverseOnSurface = cabifyDarkInverseOnSurface,
    surfaceVariant = cabifyDarkSurfaceVariant,
    onSurfaceVariant = cabifyDarkOnSurfaceVariant,
    outline = cabifyDarkOutline
)

private val cabifyLightColorScheme = lightColorScheme(
    primary = cabifyLightPrimary,
    onPrimary = cabifyLightOnPrimary,
    primaryContainer = cabifyLightPrimaryContainer,
    onPrimaryContainer = cabifyLightOnPrimaryContainer,
    inversePrimary = cabifyLightPrimaryInverse,
    secondary = cabifyLightSecondary,
    onSecondary = cabifyLightOnSecondary,
    secondaryContainer = cabifyLightSecondaryContainer,
    onSecondaryContainer = cabifyLightOnSecondaryContainer,
    tertiary = cabifyLightTertiary,
    onTertiary = cabifyLightOnTertiary,
    tertiaryContainer = cabifyLightTertiaryContainer,
    onTertiaryContainer = cabifyLightOnTertiaryContainer,
    error = cabifyLightError,
    onError = cabifyLightOnError,
    errorContainer = cabifyLightErrorContainer,
    onErrorContainer = cabifyLightOnErrorContainer,
    background = cabifyLightBackground,
    onBackground = cabifyLightOnBackground,
    surface = cabifyLightSurface,
    onSurface = cabifyLightOnSurface,
    inverseSurface = cabifyLightInverseSurface,
    inverseOnSurface = cabifyLightInverseOnSurface,
    surfaceVariant = cabifyLightSurfaceVariant,
    onSurfaceVariant = cabifyLightOnSurfaceVariant,
    outline = cabifyLightOutline
)

@Composable
fun CabifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val cabifyColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> cabifyDarkColorScheme
        else -> cabifyLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = cabifyColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = cabifyColorScheme,
        typography = cabifyTypography,
        shapes = shapes,
        content = content
    )
}
