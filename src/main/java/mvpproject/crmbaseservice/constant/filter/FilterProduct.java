package mvpproject.crmbaseservice.constant.filter;

import java.util.Arrays;

public enum FilterProduct {
    MONTH,
    WEEK,
    YEAR;
    public static FilterProduct getFilterProduct(String filter) {
        return Arrays.stream(FilterProduct.values())
                .filter(fp -> fp.name().equalsIgnoreCase(filter))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid filter: " + filter));
    }
}
