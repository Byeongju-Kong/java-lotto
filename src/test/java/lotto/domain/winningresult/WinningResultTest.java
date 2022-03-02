package lotto.domain.winningresult;

import lotto.domain.matchkind.LottoMatchKind;
import lotto.domain.purchaseamount.TotalPurchaseAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WinningResultTest {
    @Test
    @DisplayName("수익률을 반환한다.")
    void getProfitRate_Test() {
        //given
        final Map<LottoMatchKind, Integer> winningNumberByMatchKind = Map.of(
                LottoMatchKind.LOWER_THAN_THREE, 0,
                LottoMatchKind.THREE, 1,
                LottoMatchKind.FOUR, 1,
                LottoMatchKind.FIVE, 1,
                LottoMatchKind.FIVE_BONUS, 1,
                LottoMatchKind.SIX, 1);
        final TotalPurchaseAmount totalPurchaseAmount = new TotalPurchaseAmount("5000");
        final WinningResult winningResult = new WinningResult(winningNumberByMatchKind, totalPurchaseAmount);
        final double expected = 2031555000 / (double) 5000;
        //when
        final double actual = winningResult.getProfitRate();
        //then
        assertThat(actual).isEqualTo(expected);
    }
}