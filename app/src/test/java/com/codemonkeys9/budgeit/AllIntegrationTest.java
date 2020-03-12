package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.integrationtests.IntegrationTests;
import com.codemonkeys9.budgeit.logiclayer.uicalculator.IntegrationUIEntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.IntegrationUICategoryCreatorTest;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.IntegrationUICategoryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.IntegrationUICategoryModifierTest;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.IntegrationUIEntryCategorizerTest;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.IntegrationUIEntryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.IntegrationUIEntryManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegrationUIEntryFetcherTest.class,
        IntegrationUIEntryCalculatorTest.class,
        IntegrationUIEntryManagerTest.class,
        IntegrationUICategoryCreatorTest.class,
        IntegrationUICategoryFetcherTest.class,
        IntegrationUIEntryCategorizerTest.class,
        IntegrationUICategoryModifierTest.class,
        IntegrationTests.class
})

public class AllIntegrationTest {
}
