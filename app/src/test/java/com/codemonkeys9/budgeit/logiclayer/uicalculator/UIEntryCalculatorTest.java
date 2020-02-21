package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UIEntryCalculatorTest {
    @Before
    public void resetDatabase() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = DatabaseHolder.class.getDeclaredField("db");
        instance.setAccessible(true);
        instance.set(null, null);
        DatabaseHolder.init();
    }
    @Test
    public void calculateTotalIncomeWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotalIncome("1999-02-01", "2000-03-23");

        assertTrue(amount.getDisplay().equals("100.92"));
    }

    @Test
    public void calculateTotalIncomePastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotalIncome();

        assertTrue(amount.getDisplay().equals("101.91"));
    }

    @Test
    public void calculateTotalPurchaseWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotalPurchases("1999-02-01", "2000-03-23");

        assertTrue(amount.getDisplay().equals(".00"));
    }

    @Test
    public void calculateTotalPurchasePastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotalPurchases();

        assertTrue(amount.getDisplay().equals(".00"));
    }

    @Test
    public void calculateTotalWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotal("1999-02-01", "2000-03-23");

        assertTrue(amount.getDisplay().equals(".00"));
    }

    @Test
    public void calculateTotalPastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UICalculator entryCalculator = UICalculatorFactory.createUICalculator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        Amount amount = entryCalculator.calculateTotal();

        assertTrue(amount.getDisplay().equals(".00"));
    }
}
