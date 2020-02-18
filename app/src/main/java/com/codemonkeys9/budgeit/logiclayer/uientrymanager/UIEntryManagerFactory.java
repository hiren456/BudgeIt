package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverterFactory;

public class UIEntryManagerFactory {
    public static UIEntryManager createUIEntryManager(){
        ParameterConverter converter = ParameterConverterFactory.createParameterConverter();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        return new DefaultUIEntryManager(converter,entryCreator);
    }
}
