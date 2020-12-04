import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@lombok.Data
@Accessors(chain = true)
public class Data {
    /**
     * 单位净值
     */
    private BigDecimal unit;

    /**
     * 日增长率
     */
    private BigDecimal rate;
    /**
     * 累计净值
     */
    private BigDecimal cumulative;
    /**
     * 时间
     */
    private LocalDate date;
}
