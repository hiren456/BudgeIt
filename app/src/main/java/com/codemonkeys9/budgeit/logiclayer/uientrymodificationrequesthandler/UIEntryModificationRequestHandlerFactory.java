package com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler;

import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;

public class UIEntryModificationRequestHandlerFactory {
    public static UIEntryModificationRequestHandler createUIEntryModificationRequestHandler(ParameterConverter converter, EntryCreator entryCreator){
        return new DefaultUIEntryModificationRequestHandler(converter,entryCreator);
    }
}
