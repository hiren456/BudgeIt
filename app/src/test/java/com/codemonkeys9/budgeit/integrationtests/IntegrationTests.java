package com.codemonkeys9.budgeit.integrationtests;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifier;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifierFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(AndroidJUnit4.class)
public class IntegrationTests {
    Database db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createTestableDatabase(
                context,idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
        db = DatabaseHolder.getDatabase();
    }

    @After
    public void closeDb() throws IOException {
        db.clean();
        db.close();
    }

    // create cat's the modify them
    @Test
    public void createCategoriesThenModifyThemTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UICategoryModifier mod =  UICategoryModifierFactory.createUICategoryModifier();

        Amount amount1 = AmountFactory.fromString("500.00");
        Details details1 = DetailsFactory.fromString("Food");

        Amount amount2 = AmountFactory.fromString("150.00");
        Details details2 = DetailsFactory.fromString("Gas");

        Amount amount3 = AmountFactory.fromString("700.00");
        Details details3 = DetailsFactory.fromString("Phone");

        Amount amount4 = AmountFactory.fromString("20000.00");
        Details details4 = DetailsFactory.fromString("Mortgage Down Payment");

        int catID1  = creator.createBudgetCategory(amount1,details1);
        int catID2 = creator.createBudgetCategory(amount2,details2);
        int catID3 = creator.createSavingsCategory(amount3,details3);
        int catID4 = creator.createSavingsCategory(amount4,details4);

        Category cat1 = db.selectCategoryByID(catID1);
        Category cat2 = db.selectCategoryByID(catID2);
        Category cat3 = db.selectCategoryByID(catID3);
        Category cat4 = db.selectCategoryByID(catID4);

        Date today = DateFactory.fromString("now");

        assertTrue(cat1.getDateLastModified().equals(today));
        assertTrue(cat1.getGoal().equals(amount1));
        assertTrue(cat1.getName().equals(details1));

        assertTrue(cat2.getDateLastModified().equals(today));
        assertTrue(cat2.getGoal().equals(amount2));
        assertTrue(cat2.getName().equals(details2));

        assertTrue(cat3.getDateLastModified().equals(today));
        assertTrue(cat3.getGoal().equals(amount3));
        assertTrue(cat3.getName().equals(details3));

        assertTrue(cat4.getDateLastModified().equals(today));
        assertTrue(cat4.getGoal().equals(amount4));
        assertTrue(cat4.getName().equals(details4));

        Amount newAmount1 = AmountFactory.fromString("600");
        mod.changeGoal(catID1,newAmount1);

        Details newDetails2 = DetailsFactory.fromString("Car Stuff");
        mod.changeName(catID2,newDetails2);

        Amount newAmount3 = AmountFactory.fromString("600");
        mod.changeGoal(catID3,newAmount3);

        Details newDetails4 = DetailsFactory.fromString("Investment");
        mod.changeName(catID4,newDetails4);

        cat1 = db.selectCategoryByID(catID1);
        cat2 = db.selectCategoryByID(catID2);
        cat3 = db.selectCategoryByID(catID3);
        cat4 = db.selectCategoryByID(catID4);

        assertTrue(cat1.getDateLastModified().equals(today));
        assertTrue(cat1.getGoal().equals(newAmount1));
        assertTrue(cat1.getName().equals(details1));

        assertTrue(cat2.getDateLastModified().equals(today));
        assertTrue(cat2.getGoal().equals(amount2));
        assertTrue(cat2.getName().equals(newDetails2));

        assertTrue(cat3.getDateLastModified().equals(today));
        assertTrue(cat3.getGoal().equals(newAmount3));
        assertTrue(cat3.getName().equals(details3));

        assertTrue(cat4.getDateLastModified().equals(today));
        assertTrue(cat4.getGoal().equals(amount4));
        assertTrue(cat4.getName().equals(newDetails4));
    }

    @Test
    public void createCategoriesThenDeleteThemThenTryAndSearchForItTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UICategoryModifier mod =  UICategoryModifierFactory.createUICategoryModifier();
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();

        Amount amount1 = AmountFactory.fromString("500.00");
        Details details1 = DetailsFactory.fromString("Food");

        Amount amount2 = AmountFactory.fromString("150.00");
        Details details2 = DetailsFactory.fromString("Gas");

        Amount amount3 = AmountFactory.fromString("700.00");
        Details details3 = DetailsFactory.fromString("Phone");

        Amount amount4 = AmountFactory.fromString("20000.00");
        Details details4 = DetailsFactory.fromString("Mortgage Down Payment");

        int catID1  = creator.createBudgetCategory(amount1,details1);
        int catID2 = creator.createBudgetCategory(amount2,details2);
        int catID3 = creator.createSavingsCategory(amount3,details3);
        int catID4 = creator.createSavingsCategory(amount4,details4);

        mod.deleteCategory(catID3);

        assertNull(db.selectCategoryByID(catID3));
    }

    // create entrys then fetch them
    @Test
    public void createEntyiesThenFetchThemTest(){
        UIEntryManager man = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        man.createEntry("450","Scholarship","2019-01-23",false);
        man.createEntry("900","RTX 2070","2019-01-24",true);
        man.createEntry("300","M2-SSD","2019-04-23",true);
        man.createEntry("1777","Tax Refund","2019-04-24",false);


        EntryList all = fetcher.fetchAllEntrys();
        EntryList allPastJan = fetcher.fetchAllEntrys("2019-02-01","now");
        EntryList allBeforeApr = fetcher.fetchAllEntrys("past","2019-03-10");

        EntryList inc = fetcher.fetchAllIncomeEntrys();
        EntryList incPastJan = fetcher.fetchAllIncomeEntrys("2019-02-01","now");
        EntryList incBeforeApr = fetcher.fetchAllIncomeEntrys("past","2019-03-10");

        EntryList pur = fetcher.fetchAllPurchaseEntrys();
        EntryList purPastJan = fetcher.fetchAllPurchaseEntrys("2019-02-01","now");
        EntryList purBeforeApr = fetcher.fetchAllPurchaseEntrys("past","2019-03-10");

        List<BaseEntry> list = all.getChrono();
        assertEquals(4,all.size());

        assertTrue(list.get(0).getAmount().getDisplay().equals("450.00"));
        assertTrue(list.get(0).getDetails().getValue().equals("Scholarship"));
        assertTrue(list.get(0).getDate().getYear() == 2019);
        assertTrue(list.get(0).getDate().getMonth() == 01);
        assertTrue(list.get(0).getDate().getDay() == 23);
        assertTrue(list.get(0) instanceof Income);

        assertTrue(list.get(1).getAmount().getDisplay().equals("900.00"));
        assertTrue(list.get(1).getDetails().getValue().equals("RTX 2070"));
        assertTrue(list.get(1).getDate().getYear() == 2019);
        assertTrue(list.get(1).getDate().getMonth() == 01);
        assertTrue(list.get(1).getDate().getDay() == 24);
        assertTrue(list.get(1) instanceof Purchase);

        assertTrue(list.get(2).getAmount().getDisplay().equals("300.00"));
        assertTrue(list.get(2).getDetails().getValue().equals("M2-SSD"));
        assertTrue(list.get(2).getDate().getYear() == 2019);
        assertTrue(list.get(2).getDate().getMonth() == 04);
        assertTrue(list.get(2).getDate().getDay() == 23);
        assertTrue(list.get(2) instanceof Purchase);

        assertTrue(list.get(3).getAmount().getDisplay().equals("1777.00"));
        assertTrue(list.get(3).getDetails().getValue().equals("Tax Refund"));
        assertTrue(list.get(3).getDate().getYear() == 2019);
        assertTrue(list.get(3).getDate().getMonth() == 04);
        assertTrue(list.get(3).getDate().getDay() == 24);
        assertTrue(list.get(3) instanceof Income);

        //Check accros lists
        List<BaseEntry> compList = allBeforeApr.getChrono();
        assertEquals(2,compList.size());
        assertTrue(list.get(0).equals(compList.get(0)));
        assertTrue(list.get(1).equals(compList.get(1)));
        compList = allPastJan.getChrono();
        assertEquals(2,compList.size());
        assertTrue(list.get(2).equals(compList.get(0)));
        assertTrue(list.get(3).equals(compList.get(1)));
        compList = inc.getChrono();
        assertEquals(2,compList.size());
        assertTrue(list.get(0).equals(compList.get(0)));
        assertTrue(list.get(3).equals(compList.get(1)));
        compList = incBeforeApr.getChrono();
        assertEquals(1,compList.size());
        assertTrue(list.get(0).equals(compList.get(0)));
        compList = incPastJan.getChrono();
        assertEquals(1,compList.size());
        assertTrue(list.get(3).equals(compList.get(0)));
        compList = pur.getChrono();
        assertEquals(2,compList.size());
        assertTrue(list.get(1).equals(compList.get(0)));
        assertTrue(list.get(2).equals(compList.get(1)));
        compList = purBeforeApr.getChrono();
        assertEquals(1,compList.size());
        assertTrue(list.get(1).equals(compList.get(0)));
        compList = purPastJan.getChrono();
        assertEquals(1,compList.size());
        assertTrue(list.get(2).equals(compList.get(0)));
    }


    // create cats then fetch them
    @Test
    public void createCategoriesThenFetchThemTest(){
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        UICategoryCreator man = UICategoryCreatorFactory.createUICategoryCreator();

        Amount amount1 = AmountFactory.fromString("500.00");
        Details details1 = DetailsFactory.fromString("Food");

        Amount amount2 = AmountFactory.fromString("150.00");
        Details details2 = DetailsFactory.fromString("Gas");

        Amount amount3 = AmountFactory.fromString("700.00");
        Details details3 = DetailsFactory.fromString("Phone");

        Amount amount4 = AmountFactory.fromString("20000.00");
        Details details4 = DetailsFactory.fromString("Mortgage Down Payment");

        int catID1  = man.createBudgetCategory(amount1,details1);
        int catID2 = man.createBudgetCategory(amount2,details2);
        int catID3 = man.createSavingsCategory(amount3,details3);
        int catID4 = man.createSavingsCategory(amount4,details4);

        CategoryList all = fetcher.fetchAllCategories();
        CategoryList bud = fetcher.fetchAllBudgetCategories();
        CategoryList sav = fetcher.fetchAllSavingsCategories();

        assertEquals(4,all.size());
        assertEquals(2,bud.size());
        assertEquals(2,sav.size());

        //this is pretty hard to test actually. Instead of using the creator
        // we could insert it directly with the db, but then that is pretty much a unit test
    }
}
