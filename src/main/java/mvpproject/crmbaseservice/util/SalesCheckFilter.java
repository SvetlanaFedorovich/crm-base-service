package mvpproject.crmbaseservice.util;

import mvpproject.crmbaseservice.constant.filter.FilterProduct;

import java.time.LocalDate;

public class SalesCheckFilter {
    public static LocalDate checkFilter(FilterProduct filter) {
        var today = LocalDate.now();
        LocalDate period;
        switch (filter) {
            case WEEK:
                period = today.minusWeeks(1);
                break;
            case MONTH:
                period = today.minusMonths(1);
                break;
            case YEAR:
                period = today.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Unexpected filter: " + filter);
        }
        return period;
    }
}
