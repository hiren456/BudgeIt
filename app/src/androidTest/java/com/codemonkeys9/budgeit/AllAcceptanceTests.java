package com.codemonkeys9.budgeit;


import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.ui.BudgetCategoryTest;
import com.codemonkeys9.budgeit.ui.DeleteCategoryTest;
import com.codemonkeys9.budgeit.ui.DeleteEntryTest;
import com.codemonkeys9.budgeit.ui.FlagEntryTest;
import com.codemonkeys9.budgeit.ui.IncomeTest;
import com.codemonkeys9.budgeit.ui.PurchaseTest;
import com.codemonkeys9.budgeit.ui.PurchaseTimelineTest;
import com.codemonkeys9.budgeit.ui.SavingsCategoryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BudgetCategoryTest.class,
        DeleteCategoryTest.class,
        DeleteEntryTest.class,
        FlagEntryTest.class,
        IncomeTest.class,
        PurchaseTest.class,
        PurchaseTimelineTest.class,
        SavingsCategoryTest.class
})
public class AllAcceptanceTests {
}
