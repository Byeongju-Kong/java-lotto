package lotto.domain.lottonumber;

import lotto.domain.lottonumber.vo.LottoNumber;
import lotto.domain.matchkind.LottoMatchKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumbersTest {
    @Test
    @DisplayName("당첨 번호와 보너스 번호에 중복이 있으면 예외를 발생시킨다.")
    void create_ExceptionByDuplicationOfTargetNumbersAndBonusNumber() {
        //given
        final Lotto targetNumbers = new Lotto(Arrays.asList("1", "2", "3", "4", "5", "6"));
        final LottoNumber bonusNumber = LottoNumber.from("1");
        final String expectedExceptionMessage = "당첨 번호와 보너스 번호에 중복이 있으면 안됩니다.";
        //when then
        assertThatThrownBy(() -> new WinningNumbers(targetNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMessage);
    }

    @ParameterizedTest
    @DisplayName("로또 숫자 6개를 받아 당첨 번호와 보너스 번호를 비교해 당첨 종류를 반환한다.")
    @MethodSource("provideLottoNumbersAndMatchKind")
    void getLottoMatchResult(final List<String> numbers, final LottoMatchKind expected) {
        //given
        final Lotto targetNumbers = new Lotto(Arrays.asList("1", "2", "3", "4", "5", "6"));
        final LottoNumber bonusNumber = LottoNumber.from("45");
        final WinningNumbers winningNumbers = new WinningNumbers(targetNumbers, bonusNumber);
        final Lotto lotto = new Lotto(numbers);
        //when
        final LottoMatchKind actual = winningNumbers.getLottoMatchResult(lotto);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideLottoNumbersAndMatchKind() {
        return Stream.of(
                Arguments.of(Arrays.asList("1", "2", "3", "4", "5", "6"), LottoMatchKind.SIX),
                Arguments.of(Arrays.asList("2", "3", "4", "5", "6", "45"), LottoMatchKind.FIVE_BONUS),
                Arguments.of(Arrays.asList("2", "3", "4", "5", "6", "7"), LottoMatchKind.FIVE),
                Arguments.of(Arrays.asList("3", "4", "5", "6", "7", "45"), LottoMatchKind.FIVE),
                Arguments.of(Arrays.asList("3", "4", "5", "6", "7", "8"), LottoMatchKind.FOUR),
                Arguments.of(Arrays.asList("4", "5", "6", "7", "8", "45"), LottoMatchKind.FOUR),
                Arguments.of(Arrays.asList("4", "5", "6", "7", "8", "9"), LottoMatchKind.THREE),
                Arguments.of(Arrays.asList("5", "6", "7", "8", "9", "45"), LottoMatchKind.THREE),
                Arguments.of(Arrays.asList("7", "8", "9", "10", "11", "12"), LottoMatchKind.LOWER_THAN_THREE),
                Arguments.of(Arrays.asList("8", "9", "10", "11", "12", "13"), LottoMatchKind.LOWER_THAN_THREE),
                Arguments.of(Arrays.asList("9", "10", "11", "12", "13", "14"), LottoMatchKind.LOWER_THAN_THREE),
                Arguments.of(Arrays.asList("10", "11", "12", "13", "14", "15"), LottoMatchKind.LOWER_THAN_THREE)
        );
    }
}