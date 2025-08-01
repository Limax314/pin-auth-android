package com.sharpedge.pintextfield

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @file ComposePinInput.kt
 * @brief Jetpack Compose 환경에서 사용할 수 있는 사용자 정의 PIN 입력 필드를 제공합니다.
 * @detail
 * 이 파일은 `ComposePinInput`이라는 재사용 가능한 Composable 함수를 핵심으로 포함하고 있습니다.
 * 개발자는 이 컴포저블을 사용하여 앱에 PIN 또는 OTP(일회용 비밀번호) 입력 UI를 쉽게 구현할 수
 * 있습니다.
 *
 * [주요 기능]
 * - 2가지 스타일 지원: 각 입력 셀이 사각형 모양인 'BOX' 스타일과 밑줄이 쳐진 'UNDERLNE' 스타일을
 * 제공합니다.
 * - 높은 커스터마이징: 셀의 크기, 모양, 색상, 테두리 두께, 폰트 크기 등 대부분의 시각적 요소를
 * 파라미터를 통해 자유롭게 설정할 수 있습니다.
 * - 상태 호이스팅(State Hoisting): `value`와 `onValueChange` 콜백 패턴을 사용하여 Composable
 * 외부에서 상태를 관리하므로, 더 유연하고 예측 가능한 UI를 만들 수 있습니다.
 * - 입력 완료 콜백: 사용자가 정해진 길이의 PIN을 모두 입력했을 때(`onPinEntered`) 특정 로직을
 * 수행하도록 할 수 있습니다.
 * - 마스킹 기능: 입력된 문자를 지정된 마스크 문자(예: '*')로 가려 개인정보를 보호할 수 있습니다.
 */

/**
 * PIN 입력 UI 스타일을 정의하는 enum 클래스
 *
 * - [BOX]: 각 PIN 입력란이 박스 형태의 테두리로 표시됩니다.
 * - [UNDERLINE]: 각 PIN 입력란이 밑줄 형태로 표시됩니다.
 *
 * @see ComposePinInputStyle.BOX
 * @see ComposePinInputStyle.UNDERLINE
 */
enum class ComposePinInputStyle {
    BOX,
    UNDERLINE
}

/**
 * 사용자 정의 가능한 PIN(개인 식별 번호) 입력 필드를 생성하는 Composable 함수
 *
 * 박스 및 밑줄 스타일을 포함한 다양한 스타일 옵션을 허용하며, 값 변경 및 입력 완료에
 * 대한 콜백을 제공합니다.
 *
 * @param value 현재 PIN 입력 값
 * @param onValueChange PIN 값이 변경될 때 호출되는 콜백 함수
 * @param maxSize PIN의 최대 길이(기본값: 4)
 * @param mask PIN을 마스킹할 문자(null일 경우 실제 문자가 표시)
 * @param isError 오류 상태 여부(true일 경우 오류 스타일 적용)
 * @param onPinEntered PIN 입력이 완료되었을 때 호출되는 콜백 함수
 * @param cellShape 각 PIN 입력 셀의 모양, [ComposePinInputStyle.BOX] 스타일에서 사용
 * @param fontColor PIN 텍스트의 색상
 * @param cellBorderColor PIN 입력 셀의 테두리 색상
 * @param rowPadding PIN 입력 필드 전체의 패딩 값
 * @param cellPadding 각 PIN 입력 셀 사이의 간격
 * @param cellSize 각 PIN 입력 실의 크기
 * @param cellBorderWidth PIN 입력 셀의 테두리 두께, [ComposePinInputStyle.BOX] 스타일에서 사용
 * @param textFontSize PIN 텍스트의 글자 크기
 * @param focusedCellBorderColor 포커스된 PIN 입력 셀의 테두리 색상
 * @param errorBorderColor 오류 상태일 때의 테두리 색상
 * @param cellBackgroundColor 각 PIN 입력 셀의 배경색
 * @param cellColorOnSelect 문자가 입력된 셀의 배경색
 * @param borderThickness 밑줄의 두께, [ComposePinInputStyle.UNDERLINE] 스타일에서 사용
 * @param style PIN 입력 UI의 스타일, [ComposePinInputStyle.BOX] 또는
 * [ComposePinInputStyle.UNDERLINE] 사용 가능
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun ComposePinInput(
    value: String,
    onValueChange: (String) -> Unit,
    maxSize: Int = 4,
    mask: Char? = null,
    isError: Boolean = false,
    onPinEntered: ((String) -> Unit)? = null,
    cellShape: Shape = RoundedCornerShape(4.dp),
    fontColor: Color = Color.Blue,
    cellBorderColor: Color = Color.Gray,
    rowPadding: Dp = 8.dp,
    cellPadding: Dp = 16.dp,
    cellSize: Dp = 50.dp,
    cellBorderWidth: Dp = 1.dp,
    textFontSize: TextUnit = 20.sp,
    focusedCellBorderColor: Color = Color.DarkGray,
    errorBorderColor: Color = Color.Red,
    cellBackgroundColor: Color = Color.Transparent,
    cellColorOnSelect: Color = Color.Transparent,
    borderThickness: Dp = 2.dp,
    style: ComposePinInputStyle = ComposePinInputStyle.BOX
) {
    /**
     * 소프트웨어 키보드의 가시성을 제어하는 컨트롤러
     *
     * PIN 입력 완료시 키보드를 숨기는데 사용됩니다.
     */
    val keyboardController = LocalSoftwareKeyboardController.current

    /**
     * PIN 입력 필드의 포커스 상태를 추적하는 변수
     *
     * 현재 입력 중인 셀을 시각적으로 강조하는데 사용됩니다.
     */
    val focusedState = remember { mutableStateOf(false) }

    /**
     * 포커스를 요청하는데 사용되는 객체
     *
     * 사용자가 PIN 셀을 탭했을 때 숨겨진 BasicTextField에 포커스를
     * 주기 위해 사용됩니다.
     */
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()

        // 키보드 표시 로직 `onFocusChanged`에서 일괄 처리
        // keyboardController?.show()
    }

    /**
     * PIN 입력 필드의 전체 레이아웃을 감싸는 컨테이너입니다.
     *
     * 이 Box는 숨겨진 `BasicTextField`와 사용자가 실제로 보는 PIN 셀들을 중앙에
     * 정렬시키는 역할을 합니다.
     * `fillMaxWidth()` 수식어를 통해 부모 컴포저블의 가로 폭을 모두 차지하도록 하여
     * 내부 요소들이 화면 너비에 맞춰 정렬될 수 있는 기준을 제공합니다.
     *
     * @param contentAlignment 내부 콘텐츠(TextField, PIN 셀)를 중앙으로 정렬합니다.
     * @param modifier 너비를 최대로 확장하는 수식어를 적용합니다.
     */
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        /**
         * 실제 텍스트 입력을 처리하는 숨겨진 TextField입니다.
         * 투명하게 처리되어 사용자에게는 보이지 않지만, 키보드 입력 및 포커스 관리를
         * 담당합니다.
         *
         * @param value 현재 입력된 PIN 값, `onValueChange`를 통해 업데이트됩니다.
         * @param onValueChange 사용자가 텍스트를 입력할 때마다 호출되는 콜백입니다. 입력된
         * 전체 문자열을 파라미터로 받습니다.
         * @param keyboardOptions 키보드의 종류(숫자)와 입력 완료(IME) 액션을 설정합니다.
         * @param keyboardActions 키보드의 '완료' 액션 버튼을 눌렀을 때의 동작(키보드 숨김)을
         * 정의합니다.
         * @param modifier TextField를 숨기고 포커스 관련 로직을 처리하기 위한 수식어 체인입니다.
         * @param textStyle 텍스트 색상을 투명하게 만들어 화면에 보이지 않도록 설정합니다.
         */
        BasicTextField(
            value = value,
            onValueChange = { text ->
                // 입력된 텍스트 길이가 최대 길이를 넘지 않도록 합니다.
                if (text.length <= maxSize) {
                    onValueChange(text)

                    // 텍스트 길이가 최대 길이에 도달하면 onPinEntered 콜백을 호출합니다.
                    if (text.length == maxSize) onPinEntered?.invoke(text)
                }
            },
            /**
             * @param keyboardOptions 키보드의 종류와 입력 완료(IME) 액션을 설정합니다.
             * - `keyboardType = KeyboardType.Number`: 사용자에게 숫자 전용 키보드를 표시합니다.
             * - `imeAction = ImeAction.Done`: 키보드 오른쪽 하단에 '완료' 버튼을 표시하여,
             * 입력이 끝났음을 명시적으로 알릴 수 있게 합니다.
             */
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, // 숫자 키보드를 사용합니다.
                imeAction = ImeAction.Done // 입력 완료 액션을 설정합니다.
            ),
            keyboardActions = KeyboardActions(onDone = {
                // '완료' 액션시 키보드를 숨깁니다.
                keyboardController?.hide()
            }),
            modifier = Modifier
                .alpha(0.01f) // TextField를 거의 투명하게 만들어 숨깁니다.
                .onFocusChanged {
                    // 포커스 상태가 변경될 때 focusedState를 업데이트합니다.
                    focusedState.value = it.isFocused
                }
                .focusRequester(focusRequester), // focusRequester와 연결합니다.
            textStyle = TextStyle.Default.copy(color = Color.Transparent)
            //: 텍스트 색상을 투명하게 설정합니다.
        )

        // PIN 입력을 시각적으로 표시하는 UI 부분입니다.
        val boxWidth = cellSize

        /**
         * @param horizontalArrangement 자식 요소(PIN 셀)들을 수평으로 정렬하는 방식을 정의합니다.
         * - `Arrangement.spaceBy(cellPadding)`: 각 셀 사이에 `cellPadding`만큼의 고정된 간격을
         * 둡니다.
         * @param modifier 전체 Row에 대한 패딩을 적용합니다.
         */
        Row(
            horizontalArrangement = Arrangement.spacedBy(cellPadding),
            modifier = Modifier.padding(rowPadding)
        ) {
            // maxSize만큼 반복하여 각 PIN 셀을 그립니다.
            repeat(maxSize) { index ->
                // 현재 셀이 활성화(포커스) 상태인지 여부를 결정합니다.
                // 포커스가 있고, 현재 인덱스가 입력된 텍스트의 길이와 같을 때 활성화됩니다.
                val isActiveBox = focusedState.value && (index == value.length)

                if (style == ComposePinInputStyle.BOX) {
                    // 'BOX' 스타일의 PIN 필드 로직입니다.
                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .background(
                                // 문자가 입력된 셀과 그렇지 않은 셀의 배경색을 다르게 설정합니다.
                                color = if (index < value.length) cellColorOnSelect
                                else cellBackgroundColor,
                                shape = cellShape
                            )
                            .border(
                                width = cellBorderWidth,
                                color = when {
                                    isError -> errorBorderColor // 오류 상태일 때
                                    isActiveBox -> focusedCellBorderColor // 활성화 상태일 때
                                    else -> cellBorderColor // 기본 상태일 때
                                },
                                shape = cellShape
                            )
                            .clickable(
                                indication = null, // 클릭시 물결 효과를 제거합니다.
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                // 클릭시 숨겨진 TextField에 포커스를 요청합니다.
                                focusRequester.requestFocus()

                                // 키보드를 명시적으로 표시
                                keyboardController?.show()
                            }
                    ) {
                        // 현재 인덱스에 해당하는 문자가 있으면 표시합니다.
                        if (index < value.length) {
                            // 마스킹 문자가 설정되었으면 마스킹 문자를, 아니면 실제 문자를 표시합니다.
                            val displayChar = mask ?: value[index]

                            Text(
                                text = displayChar.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = textFontSize,
                                color = fontColor
                            )
                        }
                    }
                } else {
                    // 'UNDERLINE' 스타일의 PIN 필드 로직입니다.
                    Box(
                        modifier = Modifier
                            .size(boxWidth, cellSize + borderThickness)
                            .background(
                                color = if (index < value.length) cellColorOnSelect
                                else cellBackgroundColor
                            )
                            .clickable(
                                indication = null, // 클릭시 물결 효과를 제거합니다.
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                // 클릭시 숨겨진 TextField에 포커스를 요청합니다.
                                focusRequester.requestFocus()

                                // 키보드를 명시적으로 표시
                                keyboardController?.show()
                            }
                    ) {
                        // Canvas를 사용하여 밑줄을 그립니다.
                        Canvas(
                            modifier = Modifier.fillMaxSize(),
                            onDraw = {
                                drawLine(
                                    color = when {
                                        isError -> errorBorderColor // 오류 상태일 때
                                        isActiveBox -> focusedCellBorderColor // 활성화 상태일 때
                                        else -> cellBorderColor // 기본 상태일 때
                                    },
                                    start = Offset(
                                        x = 0f,
                                        y = size.height - borderThickness.toPx()
                                    ),
                                    end = Offset(
                                        x = size.width,
                                        y = size.height - borderThickness.toPx()
                                    ),
                                    strokeWidth = borderThickness.toPx()
                                )
                            }
                        )

                        // 현재 인덱스에 해당하는 문자가 있으면 표시합니다.
                        if (index < value.length) {
                            val displayChar = mask ?: value[index]
                            val lineHeightDp: Dp = with(LocalDensity.current) {
                                textFontSize.toDp()
                            }

                            Text(
                                text = displayChar.toString(),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = (cellSize - lineHeightDp) / 2),
                                //: 텍스트를 수직 중앙에 가깝게 배치합니다.
                                fontSize = textFontSize,
                                color = fontColor
                            )
                        }
                    }
                }
            }
        }
    }
}