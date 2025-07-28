# Android 프로젝트 구조

이 프로젝트는 안드로이드의 **멀티 모듈 아키텍처(Multi-module Architecture)** 를 따르고 있으며,
각 모듈은 다음과 같은 명확한 역할을 수행합니다.

| 모듈명                   | 역할                                                                           | 비유         |
|-----------------------|------------------------------------------------------------------------------|------------|
| **`app`**             | 최종 사용자가 설치하는 **애플리케이션**입니다.`PinTextFieldLib`를 가져와 실제 화면에 구현합니다.              | 완성된 자동차    |
| **`PinTextFieldLib`** | PIN 입력 UI 컴포넌트를 제공하는 **재사용 가능한 라이브러리**입니다. 독립적으로 존재하며 다른 프로젝트에서도 사용할 수 있습니다. | 자동차 부품(엔진) |
| **`Gradle Scripts`**  | 프로젝트의 빌드 방식과 모듈 간의 의존성을 정의하는 **설계도** 역할을 합니다.                                | 조립 설명서     |

### 📂 app, PinTextFieldLib 모듈 상세 구조

* `manifests`: 애플리케이션의 '신분증' 또는 '설정 요약'과 같은 역할을 합니다. 안드로이드 OS나 Google Play Store가 앱을 실행하거나 정보를 파악하기
  위해 반드시 필요한 필수 정보를 담고 있습니다.
* `kotlin+java`: 애플리케이션의 '두뇌'에 해당합니다. 앱의 모든 로직, 즉 '어떻게 동작할 것인가'를 정의하는 Kotlin 또는 Java 코드가 위치합니다.
* `res`: 애플리케이션의 '외형'과 '자료'를 담당합니다. 코드(로직)와 UI 리소스(데이터)를 분리하여 관리의 효율성을 높입니다. 예를 들어, 앱의 텍스트를 수정할 때
  코드를 건드리지 않고 `res/values/strings.xml`만 수정하면 되므로 유지보수가 용이해집니다. `PinTextFieldLib` 모듈에는 존재하지 않습니다.
* `res (generated)`: `이 폴더는 개발자가 직접 수정하는 곳이 아닙니다.` 안드로이드 빌드 시스템이 프로젝트를 컴파일하는 과정에서 자동으로 생성하는 리소스 파일들이
  위치하는 곳입니다.

### 📂 Gradle Scripts 상세 구조

* `build.gradle.kts` (Project: `pin-auth-android`): 프로젝트 전체의 '총괄 건설 현장 관리자'입니다. 모든 하위 모듈(`app`,
  `PinTextFieldLib`)이 공통으로 사용할 도구(플러그인) 버전과 라이브러리를 다운로드할 저장소(예: google(), mavenCentral())를 지정합니다. 모든
  공사 현장에서 동일한 규격의 자재를 사용하도록 지시하는 것과 같습니다.
* `build.gradle.kts` (Module: `app`, `PinTextFieldLib`): 각 모듈의 '개별 시공 계획서'입니다. `app` 모듈을 어떻게 안드로이드
  앱으로 만들지, `PinTextFieldLib` 모듈을 어떻게 라이브러리로 만들지 구체적인 지침을 담고 있습니다. 필요한 부품(의존성 라이브러리), 최소 설치 사양(
  minSdk), 그리고 조립 방법(플러그인) 등을 상세히 정의합니다.
* `consumer-rules.pro` (in `PinTextFieldLib`): 라이브러리 사용자를 위한 '특별 주의사항 안내서'입니다. `PinTextFieldLib`
  라이브러리를 가져다 쓰는 `app` 모듈이나 다른 앱이 코드를 최적화할 때, "이 라이브러리의 이 부분은 절대 건드리지 마세요"라고 알려주는 역할을 합니다. 이 규칙 덕분에
  라이브러리 사용자는 라이브러리의 핵심 기능이 손상될 걱정없이 자신의 앱을 최적화할 수 있습니다.
* `proguard-rules.pro` (in `app`, `PinTextFieldLib`): '코드 최적화 및 보안 지침서'입니다. 앱을 출시할 때 코드의 크기를 줄이고(
  축소), 다른 사람이 코드를 쉽게 분석할 수 없도록 복잡하게 만들며(난독화), 사용하지 않는 코드를 제거(최적화)하는 규칙을 정의합니다. 이는 완성된 제품의 군살을 빼고 핵심
  기술이 외부에 쉽게 노출되지 않도록 보호하는 역할을 합니다.
* `gradle.properties`: 프로젝트의 '공용 상수 보관함'입니다. 프로젝트 전반에서 사용될 버전 정보나 설정값(예: libraryVersion=1.0.3)을 변수처럼
  저장해두는 곳입니다. 여러 설계도에서 동일한 수치를 참조해야할 때, 이 보관함의 값을 가져다 쓰게하여 일관성을 유지하고 변경을 용이하게 만듭니다.
* `gradle-wrapper.properties`: '공식 빌드 도구 지정서'입니다. 이 프로젝트는 반드시 지정된 버전(예: Gradle 8.13)의 빌드 도구를 사용해야한다고
  명시하는 파일입니다. 모든 개발자가 동일한 버전의 빌드 도구를 사용하게 하여 "제 컴퓨터에서는 빌드가 됐는데..."와 같은 문제를 원천적으로 방지하고 빌드 환경의 일관성을
  보장합니다.
* `local.properties`: '개인 작업 환경 설정 파일'입니다. 이 파일은 컴퓨터마다 다른 안드로이드 SDK의 설치 경로같은 민감하거나 개인적인 정보를 저장합니다. 이
  파일은 버전 관리 시스템에 포함되지 않으므로(gitignored), 각 개발자는 프로젝트의 공용 설정을 건드리지 않고 자신의 컴퓨터에 맞는 개발 환경을 구성할 수 있습니다.
* `settings.gradle.kts`: '프로젝트 구성원 명단'입니다. 이 프로젝트가 `app`과 `PinTextFieldLib`이라는 2개의 모듈로 구성되어 있음을
  Gradle 빌드 시스템에 알려주는 역할을 합니다. Gradle은 이 파일을 보고 어떤 모듈들을 빌드에 포함시켜야 할지 파악합니다.

---

# 💡 Android Studio를 통한 개발시 주의사항

## 에뮬레이터 캐시 문제 해결하기

에뮬레이터에서 코드를 수정해도 변경사항이 적용되지 않는 경우가 있습니다. 이는 에뮬레이터의 캐시나 스냅샷 기능 때문에 발생할 수 있으며, 다음과 같은 방법으로 해결할 수 있습니다.

1. Cold Boot(콜드 부팅)

   에뮬레이터를 완전히 종료했다가 다시 시작하여 캐시를 초기화합니다. 가장 먼저 시도해 볼 간단하고 효과적인 방법입니다.

    - 방법: Device Manager -> 해당 에뮬레이터의 `...` 메뉴 -> Cold Boot Now

2. Wipe Data(데이터 삭제)

   에뮬레이터의 모든 데이터를 삭제하여 초기 상태로 되돌립니다. 로그인 정보 등 모든 데이터가 사라지므로 주의가 필요합니다.

    - 방법: Device Manager -> 해당 에뮬레이터의 `...` 메뉴 -> Wipe Data

3. Clean and Rebuild Project(프로젝트 클린 및 재빌드)

   Gradle 빌드 캐시를 삭제하고 프로젝트를 다시 빌드합니다. 코드 변경사항이 제대로 반영되지 않을 때 유용합니다.

    - 방법: Android Studio 메뉴 -> Build Clean Project 후 Rebuild Project

---

# ComposePinInput - Jetpack Compose PIN 번호 입력 라이브러리

머티리얼 디자인 3원칙(Material Design 3 principles)을 사용하여 Kotlin과 Jetpack Compose로 빌드된
Android용 `ComposePinInput` 라이브러리에 오신 것을 환영합니다. 이 사용자가 지정 가능한 다목적
라이브러리를 사용하면 핀 코드, OTP 또는 비밀번호 입력에 이상적인 핀 입력 필드를 작성 UI에 쉽게
통합할 수 있습니다.

아래 GIF는 실제 애플리케이션을 사용하는 상황에서 `ComposePinInput`을 보여줍니다. GIF를 따라가면
Jetpack Compose를 사용하여 이 기능을 구현하는 방법을 보여주는 예제 코드를 확인할 수 있습니다.

<img src="pintextfield_1.gif" width="300" />

`ComposePinInput`을 Jetpack Compose 레이아웃에서 사용하는 방법은 직관적입니다. 아래 예시는 기본
설정을 보여줍니다. 이 설정에서는 입력된 핀의 상태를 유지하는 `Preview` composable 함수를 정의합니다.
composable 함수인 `ComposePinInput`은 현재 `pin`값, 값 변경을 처리하는 람다, 핀이 완전히 입력됐을 때
수행할 액션을 취합니다. 또한 셀 크기를 사용자가 지정하고 미리 정의된 스타일(예: 각 숫자 주위 박스)을
적용할 수 있습니다.

```kotlin
@Composable
fun Preview() {
    // 입력된 핀(PIN) 값을 저장하기 위한 state 변수
    var pin by remember { mutableStateOf("") }

    // 토스트(사용자에게 잠시 보여지고 사라지는 팝업) 메시지를 표시하기 위한 로컬 문맥 객체
    val context = LocalContext.current

    ComposePinInput(
        value = pin,
        onValueChange = {
            pin = it // 값이 변경되었을 때, state 변수 업데이트
        },
        cellSize = 70.dp, // 각 셀의 크기(높이, 너비)
        onPinEntered = { // PIN이 입력된 것을 알릴 때, 
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX
    )
}
```

## 별표로 입력 가려짐

다음은 사용자가 입력하는 실제 문자 대신 별표(*)를 표시하여 입력을 마스킹하는 버전입니다.

<img src="pintextfield_2.gif" width="300" />

```kotlin
@Composable
fun Preview() {
    // 입력된 핀(PIN) 값을 저장하기 위한 state 변수
    var pin by remember { mutableStateOf("") }

    // 토스트(사용자에게 잠시 보여지고 사라지는 팝업) 메시지를 표시하기 위한 로컬 문맥 객체
    val context = LocalContext.current

    ComposePinInput(
        value = pin,
        mask = '*', // Masks the input with asterisks
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },

        style = ComposePinInputStyle.BOX
    )
}
```

## 사용자 지정 셀 테두리 색상

사용자 지정 셀 테두리 색상을 입혀 `ComposePinInput`의 시각적 매력을 높이고, 셀 클릭시(focus) 색상이 변경되는 등의 기능을 추가할 수 있습니다.

<img src="pintextfield_3.gif" width="300" />

```kotlin
@Composable
fun Preview() {
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current

    ComposePinInput(
        value = pin,
        mask = '*',
        cellBorderColor = Color.Blue, // 셀/박스 테두리의 색상을 입력하기만 하면 됩니다.
        focusedCellBorderColor = Color.Magenta, // 박스를 클릭했을 때, 여기서 색상을 지정할 수 있습니다.
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX
    )
}
```

## 사용자 지정 밑줄 스타일

박스들 대신 `ComposePinInput`의 모양 변경 가능하며 간단한 밑줄도 지원됩니다. 좀 더 미묘한 모양을 위해 `ComposePinInput`은 상자 대신 밑줄 스타일을
지원합니다. 다음 예시에서는 이 스타일을 보여줍니다.

<img src="pintextfield_4.gif" width="300" />

```kotlin
@Composable
fun Preview() {
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 사용자 지정 색상이 적용된 밑줄 스타일
    ComposePinInput(
        value = pin,
        mask = '*',
        cellBorderColor = Color.DarkGray, // 셀/박스 테두리의 색상을 입력하기만 하면 됩니다.
        focusedCellBorderColor = Color.Blue, // 박스를 클릭했을 때, 여기서 색상을 지정할 수 있습니다.
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.UNDERLINE // 밑줄 스타일 적용
    )
}
```

## 동적인 배경 및 글꼴 색상

클릭된 배경색과 글꼴 색 사용자 지정

<img src="pintextfield_5.gif" width="300" />

```kotlin
@Composable
fun Preview() {
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 동적인 배경 및 글꼴 색상이 적용된 박스 스타일
    ComposePinInput(
        value = pin,
        mask = '*',
        cellBorderColor = Color.DarkGray, // 셀/박스 테두리의 색상을 입력하기만 하면 됩니다.
        focusedCellBorderColor = Color.Blue, // 박스를 클릭했을 때, 여기서 색상을 지정할 수 있습니다.
        cellColorOnSelect = Color.Cyan, // 셀을 클릭하면 해당 색상으로 변경됩니다.
        fontColor = Color.Blue, // 글꼴 색상이 해당 색상으로 변경됩니다.
        onValueChange = {
            pin = it
        },
        cellSize = 70.dp,
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX // 박스 스타일 유지
    )
}
```

## 셀 개수 및 문자 가리기의 유연성

`ComposePinInput`은 매우 유연하여 아래 그림과 같이 셀 수를 정의하고 사용자 지정 가림용 문자를 선택할 수 있습니다.

<img src="pintextfield_6.gif" width="300" />

```kotlin
@Composable
fun Preview() {
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 사용자 지정 셀 개수 및 가림용 문자
    ComposePinInput(
        value = pin,
        mask = '⚫', // 가림용 문자로 아무거나 선택하세요
        maxSize = 6, // 셀 수를 정의합니다. 이 속성을 사용하지 않는 경우 기본값은 4개입니다.
        cellSize = 45.dp, // 기본 크기는 50.dp이며 이 속성은 선택사항입니다.
        onValueChange = {
            pin = it
        },
        onPinEntered = {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        },
        style = ComposePinInputStyle.BOX // 박스 스타일 사용
    )
}
```

---

# 사용법

라이브러리를 사용하려면 다음 단계를 따르세요.

## 설치

### Gradle 설정

1. **아직 추가하지 않은 경우, 빌드 파일에 JitPack 저장소를 추가합니다.**

   저장소 끝에 있는 루트 `build.gradle`에 다음을 추가합니다.:

   ```gradle
   allprojects {
     repositories {
       ...
       maven { url 'https://jitpack.io' }
     }
   }
   ```

   **또는 `build.gradle.kts`를 사용하는 경우:**
   ```gradle
   allprojects {
      repositories {
    ...
        maven(url = "https://jitpack.io")
      }
    }
   ```

2. **의존성 추가**
   ```gradle
   dependencies {
      implementation 'com.github.sharp-edge:ComposePinInput:1.0.3'
    }
   ```

   **`build.gradle.kts`의 경우:**
   ```gradle
   dependencies {
      implementation ("com.github.sharp-edge:ComposePinInput:1.0.3")
    }
   ```

## 알려진 이슈들

현재 버전을 기준으로 라이브러리에는 다음과 같은 알려진 제약 사항이 있습니다.:

- **테마 지원**: `ComposePinInput` 컴포넌트는 중앙집중식 테마 관리를 지원하지 않습니다. 즉, 앱 테마의 색상, 글꼴 및 기타 스타일 속성을 자동으로 채택하지
  않습니다.

    - *영향*: 색상, 글꼴 크기 및 기타 스타일링에 대한 모든 매개변수는 앱의 테마 설정에 상속되지 않고 컴포넌트에 명시적으로 전달되어야 합니다.
    - *상태*: 이는 현재 릴리스에서 얄려진 디자인 제한사항입니다. 향후 버전에서는 개선된 테마 지원이 포함될 수 있습니다.

테마 기능을 개선하는 방법에 대한 기여와 제안을 환영합니다. 아이디어나 해결책이
있다면 [issue](https://github.com/sharp-edge/ComposePinInput/issues)를 열거나 Pull requests를 제출하여 공유해주세요.

새로운 문제가 발생하여 보고해주시면 다음 릴리즈에서 이를 해결하도록 노력하겠습니다.

## 버그&이슈들

이 [라이브러리](https://github.com/sharp-edge/ComposePinInput.git)에서 버그나 문제가 발견되면
저장소의 [Issues](https://github.com/sharp-edge/ComposePinInput/issues) 부분에서 보고해주세요. 새 이슈를 만들기 전에 다른
사용자가 이미 비슷한 문제를 보고했는지 확인하세요.

문제를 보고할 때는 버그가 발생한 환경, 문제를 재현하는 단계, 예상 동작, 기타 문제 해결에 도움이 될 수 있는 정보를 최대한 많이 포함해주세요. 스크린샷이나 코드 조각(
snnippet)도 큰 도움이 될 수 있습니다.

## 라이센스

이 프로젝트는 Apache License 2.0에 따라 라이센스가 부여됐습니다. - 세부사항은 [LICENSE](LICENSE) 파일을 참고하세요.