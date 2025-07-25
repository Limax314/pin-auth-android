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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun PinTextFieldPreview() {
    var pin by remember { mutableStateOf("") }

    val context = LocalContext.current

    //Text(text = "Hello world")

    Spacer(modifier = Modifier.size(20.dp))
    Spacer(modifier = Modifier.size(20.dp))

// 1
    ComposePinInput(
        value = pin,
        onValueChange = {
            pin = it
        },
        cellPadding = 16.dp,
        cellSize = 60.dp,
        onPinEntered = {
            Log.d("Pin_entered", "Pin = $it")
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX
    )

// 2
    ComposePinInput(
        value = pin,
        mask = '*',
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },

        style = ComposePinInputStyle.BOX
    )


// 3
    ComposePinInput(
        value = pin,
        mask = '*',
        cellBorderColor = Color.Blue,
        focusedCellBorderColor = Color.Magenta,
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },

        style = ComposePinInputStyle.BOX
    )

// 4
//    PinTextField(
//        value = pin,
//        mask= '*',
//        cellBorderColor = Color.DarkGray,
//        focusedCellBorderColor = Color.Blue,
//        onValueChange = {
//            pin = it
//        },
//        //boxHeight = 70.dp,
//        onPinEntered = {
//            Log.d("Pin_entered", "Pin = $it")
//            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
//        },
//
//        style = PinFieldStyle.UNDERLINE
//    )


// 5
//    PinTextField(
//        value = pin,
//        mask= '*',
//        cellBorderColor = Color.DarkGray,
//        focusedCellBorderColor = Color.Blue,
//        cellColorOnSelect = Color.Cyan,
//        fontColor = Color.Blue,
//        onValueChange = {
//            pin = it
//        },
//        //boxHeight = 70.dp,
//        onPinEntered = {
//            Log.d("Pin_entered", "Pin = $it")
//            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
//        },
//
//        style = PinFieldStyle.BOX
//    )


    // 6
    ComposePinInput(
        value = pin,
        onValueChange = {
            pin = it
        },
        mask = '⚫',
        maxSize = 6,
        cellSize = 45.dp,
        onPinEntered = {
            Log.d("Pin_entered", "Pin = $it")
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },

        style = ComposePinInputStyle.BOX
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PinTextFieldUsageTheme {
        PinTextFieldPreview()
    }
}