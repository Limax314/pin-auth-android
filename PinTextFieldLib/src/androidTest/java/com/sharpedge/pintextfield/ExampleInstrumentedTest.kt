package com.sharpedge.pintextfield

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * 안드로이드 기기 또는 에뮬레이터에서 실행되는 계측 테스트 클래스
 *
 * 이 테스트는 실제 안드로이드 환경에서 앱의 컴포넌트와 컨텍스트가 올바르게 동작하는지
 * 검증합니다.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /**
     * @note
     * `@Test` 어노테이션은 이 함수가 개별적인 테스트 케이스임을 나타냅니다.
     * JUnit 테스트 실행기(Runner)는 이 어노테이션이 붙은 함수를 찾아 실행합니다.
     */
    @Test
    fun useAppContext() {
        /**
         * @note
         * 테스트 대상 앱의 컨텍스트(Context)를 가져옵니다.
         * InstrumentationRegistry는 실행 중인 테스트 환경에 대한 접근을 제공하며
         * 이를 통해 앱의 컨텍스트, 타켓 정보 등을 얻을 수 있습니다.
         */
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        /**
         * @note
         * assertEquals(expected, actual) 함수는 두 값이 같은지 비교하여 테스트를
         * 검증합니다.
         * 여기서는 가져온 앱 컨텍스트의 패키지 이름이 "com.sharpedge.pintextfield"와
         * 일치하는지 확인합니다.
         * 만약 두 값이 다르면 테스트는 실패하게 됩니다.
         */
        assertEquals("com.sharpedge.pintextfield.test", appContext.packageName)
    }
}