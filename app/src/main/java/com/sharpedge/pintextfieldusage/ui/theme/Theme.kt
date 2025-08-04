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

/**
 * 애플리케이션의 다크 모드에 사용되는 색상 구성표
 * [darkColorScheme]을 사용하여 정의되며, 주 색상, 보조 색상, 3차 색상을 포함합니다.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * 애플리케이션의 라이트 모드에 사용되는 색상 구성표입니다.
 * [lightColorScheme]을 사용하여 정의되며, 주 색상, 보조 색상, 3차 색상을 포함합니다.
 * 다른 기본 색상(배경, 표면 등)을 재정의할 수 있는 주석 처리된 예제가 포함되어 있습니다.
 */
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
    /**
     * 테마의 색상 구성표를 결정합니다.
     *
     * 'when' 표현식을 사용하여 다음 조건에 따라 적절한 색상 구성표를 선택합니다:
     * 1. 동적 색상(`dynamicColor`)이 활성화되어 있고, Android 버전이 12(S) 이상인 경우:
     *  - 시스템의 현재 컨텍스트를 가져옵니다.
     *  - `darkTheme`이 true이면 `dynamicDarkColorScheme`을, 그렇지 않으면
     *  `dynamicLightColorScheme`을 적용합니다.
     * 2. 위 조건이 충족되지 않고 `darkTheme`이 true인 경우:
     *  - 미리 정의된 `DarkColorScheme`을 사용합니다.
     * 3. 위 조건들이 모두 충족되지 않는 경우(즉, 라이트 테마):
     *  - 미리 정의된 `LightColorScheme`을 사용합니다.
     */
    val colorScheme = when {
        // 1번 조건
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        // 2번 조건
        darkTheme -> DarkColorScheme

        // 3번 조건(else)
        else -> LightColorScheme
    }

    /**
     * 현재 Composable의 뷰를 가져옵니다.
     * 이 뷰는 상태 표시줄 색상과 같은 창 속성을 수정하는데 사용됩니다.
     */
    val view = LocalView.current

    /**
     * 이 코드가 UI 편집기(예: Android Studio 미리보기)에서 실행되고 있지 않은지 확인합니다.
     * `SideEffect`는 실제 디바이스나 에뮬레이터에서만 실행되어야 합니다.
     */
    if (!view.isInEditMode) {
        /**
         * 컴포지션이 성공적으로 완료될 때마다 실행되는 부수 효과입니다.
         * 여기서는 Compose가 아닌 Android Window 객체와 상호 작용하는데 사용됩니다.
         */
        SideEffect {
            // 현재 뷰의 컨텍스트에서 Window 객체를 가져옵니다.
            val window = (view.context as Activity).window

            // 상태 표시줄의 색상을 현재 테마의 주 색상으로 설정합니다.
            window.statusBarColor = colorScheme.primary.toArgb()

            /**
             * 상태 표시줄 아이콘(시간, 배터리 등)의 모양을 설정합니다.
             * darkTheme이 true이면 아이콘이 밝은 모양(어두운 배경용)으로,
             * false이면 어두운 모양(밝은 배경용)으로 설정됩니다.
             */
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    /**
     * MaterialTheme을 사용하여 결정된 색상 구성표와 타이포그래피를
     * 맵의 UI 콘텐츠에 적용합니다.
     */
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}