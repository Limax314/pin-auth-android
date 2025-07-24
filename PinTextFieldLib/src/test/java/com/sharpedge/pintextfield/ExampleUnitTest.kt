package com.sharpedge.pintextfield

import org.junit.Test
import org.junit.Assert.*

/**
 * 개발 머신(호스트)의 JVM에서 직접 실행되는 로컬 단위 테스트 예제 클래스입니다.
 *
 * 이 테스트는 안드로이드 프레임워크에 대한 의존성이 없으며, 순수한 Kotlin/Java 로직을
 * 빠르고 가볍게 검증하는데 사용됩니다.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    /**
     * '2 + 2' 연산이 올바르게 '4'를 반환하는지 검증하는 테스트 케이스입니다.
     *
     * @note
     * `@Test` 어노테이션은 이 함수가 JUnit 테스트 실행기에 의해
     * 테스트 케이스로 인식되고 실행되어야 함을 나타냅니다.
     *
     * 이 함수는 단위 테스트의 가장 기본적인 형태를 보여주며, 특정 입력(2 + 2)에 대해
     * 기대하는 출력(4)이 나오는지를 확인합니다.
     */
    @Test
    fun addition_isCorrect() {
        /**
         * @note
         * `assertEquals(expected, actual)` 함수는 두 값이 같은지 비교합니다.
         * - expected: 기대하는 값(여기서는 4)
         * - actual: 실제 계산된 값(여기서는 2 + 2의 결과)
         *
         * 만약 두 값이 다르면, 테스트는 실패로 처리됩니다.
         * 이 검증을 통해 테스트 프레임워크(JUnit)가 올바르게 설정되었고 기본적인 연산이
         * 예상대로 동작하는지 확인할 수 있습니다.
         */
        assertEquals(4, 2 + 2)
    }
}