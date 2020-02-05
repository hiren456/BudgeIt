package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.codemonkeys9.budgeit.LogicLayer.Database.Database;

import com.codemonkeys9.budgeit.Entry.Entry;

class DefaultEntryFetcher implements EntryFetcher {
    Database database;

    DefaultEntryFetcher(Database database){
        this.database = database;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {

        Date parsedStartDate = null;
        if( startDate.equals("past")){
            parsedStartDate = new Date(0);
        }else {
            try {
                parsedStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date parsedEndDate = null;
        if ( endDate.equals("now")){
            parsedEndDate = new Date();
        }else{
            try {
                parsedEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> initialList = database.selectByDate(parsedStartDate,parsedEndDate);
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = initialList.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount() < 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            initialList.remove(curr);
        }


        // hands list to in reverse chrological order
        Collections.reverse(initialList);
        return initialList;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(String startDate, String endDate) {
        Date parsedStartDate = null;
        if( startDate.equals("past")){
            parsedStartDate = new Date(0);
        }else {
            try {
                parsedStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date parsedEndDate = null;
        if ( endDate.equals("now")){
            parsedEndDate = new Date();
        }else{
            try {
                parsedEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> initialList = database.selectByDate(parsedStartDate,parsedEndDate);
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = initialList.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount() > 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            initialList.remove(curr);
        }

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(initialList);
        return initialList;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedStartDate = null;
        if( startDate.equals("past")){
            parsedStartDate = new Date(0);
        }else {
            try {
                parsedStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date parsedEndDate = null;
        if ( endDate.equals("now")){
            parsedEndDate = new Date();
        }else{
            try {
                parsedEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return list;
    }
}
