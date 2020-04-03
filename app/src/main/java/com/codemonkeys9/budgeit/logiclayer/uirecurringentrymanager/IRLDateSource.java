package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;

class IRLDateSource implements DateSource {
    public Date now() {
        return DateFactory.fromString("now");
    }
}
