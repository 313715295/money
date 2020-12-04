import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;


@lombok.Data
@Accessors(chain = true)
public class JsonData {

    /**
     * 净值日期
     */
    private String FSRQ;
    /**
     * 单位净值
     */
    private String DWJZ;
    /**
     * 累计净值
     */
    private String LJJZ;
    /**
     * 日增长率
     */
    private String JZZZL;

    public Data convert() {
        return new Data()
                .setUnit(Optional.ofNullable(DWJZ).filter(StringUtils::isNotBlank).map(BigDecimal::new).orElse(BigDecimal.ZERO))
                .setRate(Optional.ofNullable(JZZZL).filter(StringUtils::isNotBlank).map(BigDecimal::new).orElse(BigDecimal.ZERO))
                .setCumulative(Optional.ofNullable(LJJZ).filter(StringUtils::isNotBlank).map(BigDecimal::new).orElse(BigDecimal.ZERO))
                .setDate(DateUtil.toDate(FSRQ));
    }




}
