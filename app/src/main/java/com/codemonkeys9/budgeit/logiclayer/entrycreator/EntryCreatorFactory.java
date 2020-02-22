package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class EntryCreatorFactory {
    public static EntryCreator createEntryCreator(){
        IDManager idManager = IDManagerFactory.createIDManager();
        return new DefaultEntryCreator(idManager);
    }
}
