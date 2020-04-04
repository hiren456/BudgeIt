package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.dso.date.Date;

public interface DateSource {
    Date now();

    // A Java date is necessary here because it is expected by a Java Timer. Also, our Date DSO
    // doesn't include seconds or milliseconds, which makes it unsuitable for this task anyway.
    java.util.Date tomorrowAtMidnight();
}
