package com.sharpedge.pintextfieldusage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharpedge.pintextfield.ComposePinInputStyle
import com.sharpedge.pintextfield.ComposePinInput
import com.sharpedge.pintextfieldusage.ui.theme.PinTextFieldUsageTheme

/**
 * @remarks
 * 핀번호 성공/실패 분기 기능 추가 예정
 * 1. MainActivity에 인증 성공 여부를 저장하는 Boolean 타입의 상태 변수(예: isPinCorrect)를
 * 추가합니다.
 *
 * 2. setContent 블록 냉에서 isPinCorrect 상태에 따라 조건부로 화면을 보여줍니다.
 *  - false일 경우: PinInputScreen 표시
 *  - true일 경우: SuccessScreen 표시
 *
 * 3. PinInputScreen은 인증 성공시 호출할 람다 함수(예: onPinCorrect)를 파라미터로 받습니다.
 *
 * 4. 사용자가 올바른 PIN(111111)을 입력하면, PinInputScreen은 onPinCorrect 함수를 호출하여
 * isPinCorrect 상태를 true로 변경합니다.
 *
 * 5. 잘못된 PIN을 입력하면 이전과 같이 Toast 메시지를 표시합니다.
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
                    Column {
                        PinTextFieldPreview()
                    }
                }
            }
        }
    }
}

/**
 * 간단한 인사말을 표시하는 컴포저블 함수입니다.
 *
 * @param name 인사말에 포함될 이름입니다.
 * @param modifier 이 컴포저블에 적용할 Modifier입니다.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * `ComposePinInput` 컴포저블의 다양한 사용 예시를 보여주는 미리보기 함수입니다.
 *
 * 이 함수는 여러 스타일과 속성을 가진 `ComposePinInput` 필드들을 수직으로 나열하여
 * 라이브러리의 기능을 시연합니다. 각 필드는 동일한 상태(`pin`)를 공유하며,
 * PIN 입력이 완료되면 Toast 메시지를 표시합니다.
 */
@Composable
fun PinTextFieldPreview() {
    var pin by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Text(text = "Hello world")

    Spacer(modifier = Modifier.size(20.dp))
    Spacer(modifier = Modifier.size(20.dp))

    ComposePinInput(
        value = pin,
        mask = '⚫',
        maxSize = 6,
        cellBorderColor = Color.Blue,
        focusedCellBorderColor = Color.Magenta,
        onValueChange = {
            pin = it
        },
        cellSize = 45.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX
    )
}

/**
 * Android Studio의 디자인 탭에서 `PinTextFieldPreview`의 미리보기를 제공하는
 * 컴포저블 함수입니다.
 *
 * `PinTextFieldUsageTheme`를 적용하여 실제 앱 환경과 유사한 모습으로 UI를
 * 렌더링합니다.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PinTextFieldUsageTheme {
        PinTextFieldPreview()
    }
}