package com.codemonkeys9.budgeit.logiclayer.idmanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.exceptions.InvalidIDTypeException;

class Incrementor implements IDManager {

    @Override
    public int getInitialID(String idName) {
        int ret = 0;
        if(idName.equals("Entry") || idName.equals("Category")) {
            ret = 1;
        }else{
            throw new InvalidIDTypeException(idName);
        }

        return ret;
    }

    @Override
    public int getDefaultID(String idName) {
        int ret = 0;
        if(idName.equals("Category")) {
            ret = 0;
        }else{
            throw new InvalidIDTypeException(idName);
        }

        return ret;
    }

    @Override
    public int getNewID(String idName) {
        if(!idName.equals("Entry") && !idName.equals("Category")) {
            throw new InvalidIDTypeException(idName);
        }

        Database db = DatabaseHolder.getDatabase();
        int currID = db.getIDCounter(idName);
        int nextID = currID + 1;
        db.updateIDCounter(idName, nextID);

        return nextID;
    }
}
