package com.sharpedge.pintextfieldusage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sharpedge.pintextfield.ComposePinInputStyle
import com.sharpedge.pintextfield.ComposePinInput
import com.sharpedge.pintextfieldusage.ui.theme.PinTextFieldUsageTheme

/**
 * @remarks
 * KDoc 주석 추가 예정
 */

/**
 * @file MainActivity.kt
 * @brief `PinTextFieldLib` 라이브러리의 `ComposePinInput` 사용 예제를 보여주는
 * 메인화면입니다.
 * @detail
 * 이 파일은 `ComposePinInput` 컴포저블의 다양한 스타일과 설정을 시연하기 위한
 * 목적으로 작성되었습니다. 여러 개의 PIN 입력 필드를 수직으로 나열하여 각기 다른
 * 스타일(BOX, UNDERLINE)과 길이(maxSize)를 테스트합니다.
 */

/**
 * 앱의 메인 엑티비티 클래스입니다.
 *
 * Jectpack Compose를 사용하여 UI를 구성하며, `PinInputScreen`을 화면에 표시하는
 * 역할을 합니다.
 */
class MainActivity : ComponentActivity() {
    /**
     * 엑티비티가 처음 생성될 때 호출되는 콜백 메서드입니다.
     *
     * 여기서 `setContent`를 통해 컴포저블 UI의 루트를 설정합니다.
     *
     * @param savedInstanceState 이전에 저장된 엑티비티 상태가 포함된 Bundle 객체,
     * 엑티비티가 새로 시작되는 경우에는 null입니다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PinTextFieldUsageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PinAuthScreen()
                }
            }
        }
    }
}

/**
 * PIN 인증 상태에 따라 적절한 화면을 표시하는 컴포저블 함수입니다.
 *
 * 내부적으로 인증 성공 여부(`isPinCorrect`) 상태를 관리합니다.
 * - PIN이 아직 인증되지 않은 경우: [PinInputScreen]을 표시합니다.
 * - PIN 인증에 성공한 경우: [SuccessScreen]을 표시합니다.
 */
@Composable
fun PinAuthScreen() {
    var isPinCorrect by remember { mutableStateOf(false) }

    if (isPinCorrect) {
        SuccessScreen()
    } else {
        PinInputScreen(onPinCorrect = { isPinCorrect = true })
    }
}

/**
 *
 */
@Composable
fun PinInputScreen(onPinCorrect: () -> Unit) {
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current
    val correctPin = "111111"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PIN 번호를 입력하세요")

        ComposePinInput(
            value = pin,
            mask = '⚫',
            maxSize = 6,
            cellBorderColor = Color.Blue,
            focusedCellBorderColor = Color.Magenta,
            cellSize = 45.dp,
            style = ComposePinInputStyle.BOX,
            onValueChange = { pin = it },
            onPinEntered = { enteredPin ->
                if (enteredPin == correctPin) {
                    onPinCorrect()
                } else {
                    Toast.makeText(
                        context, "잘못된 PIN 번호입니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    pin = "" // 입력된 PIN 번호 초기화
                }
            }
        )
    }
}

@Composable
fun SuccessScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PIN 번호 인증 통과!")
    }
}