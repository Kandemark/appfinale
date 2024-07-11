package com.application.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable

@Immutable
data class AppColors(
    val primary: Color,
    val primaryLight: Color,
    val text: Color
)

val PrimaryColor = Color(0xFF4a3298)
val PrimaryLightColor = Color(0xFFffffff)
val TextColor = Color.Black // Example text color

// Extend MaterialTheme with custom colors
val MaterialTheme.appColors: AppColors
    get() = AppColors(
        primary = PrimaryColor,
        primaryLight = PrimaryLightColor,
        text = TextColor
    )

// Optional: Define a ColorScheme if needed
//@Composable
//fun MyCustomColorScheme(): ColorScheme {
//    return ColorScheme(
//        primary = PrimaryColor,
//        secondary = PrimaryLightColor,
//        background = Color.White,
//        surface = Color.White,
//        onPrimary = Color.White,
//        onSecondary = Color.White,
//        onBackground = Color.Black,
//        onSurface = Color.Black,
//        error = Color.Red
//    )
//}



//private val DarkColorPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200
//)

val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    //   val colors = if (darkTheme) {
    //      DarkColorPalette
    // } else {
    LightColorPalette
    //   }

    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

//     Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),

)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}