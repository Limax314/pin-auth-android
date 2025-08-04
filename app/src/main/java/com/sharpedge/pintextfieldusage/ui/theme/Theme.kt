package com.sharpedge.pintextfieldusage.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * @file Theme.kt
 * @brief 애플리케이션의 테마(색상 구성표, 글꼴 스타일 등)를 정의합니다.
 *
 * 이 파일은 PinTextFieldUsage 애플리케이션의 [MaterialTheme]을 설정합니다.
 * 여기에는 다크 및 라이트 색상 팔레트에 대한 정의와 시스템 설정에 따라 올바른 테마를 적용하는
 * [PinTextFieldUsageTheme] 컴포저블 함수가 포함되어 있습니다.
 * 또한 Android 12 이상에서 동적 색상 지정을 지원합니다.
 */

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * 주어진 콘텐츠에 애플리케이션의 테마를 적용하는 컴포저블 함수입니다.
 *
 * 이 함수는 적절한 색상 구성표(라이트/다크, 동적/정적)를 결정하고
 * [MaterialTheme]을 사용하여 적용합니다.
 * 또한 시스템 상태 표시줄 색상을 관리합니다.
 *
 * @param darkTheme 테마 다크 모드 설정 여부, 기본값: 시스템 설정
 * @param dynamicColor 동적 색상(Material You) 사용 여부, Android 12 이상에서 사용 가능,
 * 기본값: true
 * @param content 테마를 적용할 컴포저블 콘텐츠
 */
@Composable
fun PinTextFieldUsageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}