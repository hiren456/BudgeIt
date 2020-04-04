package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.UIEntryBuilder.UIEntryBuilder;
import com.codemonkeys9.budgeit.logiclayer.UIEntryBuilder.UIEntryBuilderFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;

import java.util.List;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentSelectedListener;

public class NewEntryActivity extends AppCompatActivity {
    // See res/values/strings.xml => "entry_types"
    private final static int
        INCOME = 0,
        EXPENSE = 1;
    Spinner categoryDropdown;
    Category category;

    UICategoryFetcher categoryFetcher;

    SegmentedControl entryTypeControl;
    Switch badSwitch;
    Switch recurringSwitch;
    EditText[] recurrenceFields;
    TextView recurringMessage;

    final int YEARS = 0, MONTHS = 1, WEEKS = 2, DAYS = 3; //for the editText array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = submitEntry();
                if(id != null) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newly_created_entry_id", (int)id);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        CategoryList categoryList = categoryFetcher.fetchAllCategories();
        List<Category> listOfCategories = categoryList.getReverseChrono();
        categoryDropdown = findViewById(R.id.spinner_category);
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, listOfCategories);
        categoryDropdown.setAdapter(categoryAdapter);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (Category)categoryDropdown.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        /* BAD SWITCH */
        this.entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        this.entryTypeControl.setSelectedSegment(0);
        this.entryTypeControl.addOnSegmentSelectListener(new OnSegmentSelectedListener() {
            @Override
            public void onSegmentSelected(SegmentViewHolder segmentViewHolder, boolean isSelected, boolean isReselected) {
                if(!isSelected) return;
                showBadSwitch(segmentViewHolder.getAbsolutePosition() == EXPENSE);
            }
        });
        this.badSwitch = findViewById(R.id.switch_bad);
        showBadSwitch(false);
        /* BAD SWITCH */

        /* RECURRENCE FIELDS */
        this.recurringSwitch = findViewById(R.id.switch_recurring);
        this.recurringSwitch.setVisibility(View.VISIBLE);
        this.recurringMessage = findViewById(R.id.text_recurring_message);
        recurrenceFields = new EditText[DAYS+1];
                    recurrenceFields[YEARS] = findViewById(R.id.recurring_years);
                    recurrenceFields[MONTHS] = findViewById(R.id.recurring_months);
                    recurrenceFields[WEEKS] = findViewById(R.id.recurring_weeks);
                    recurrenceFields[DAYS] = findViewById(R.id.recurring_days);

        showRecurringFields(false);
        recurringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showRecurringFields(isChecked);
            }
        });
        /* RECURRENCE FIELDS */

    }

    void showBadSwitch(boolean show) {
        this.badSwitch.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    void showRecurringFields(boolean show){
        for(EditText e : recurrenceFields){
            e.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
        this.recurringMessage.setVisibility(show? View.VISIBLE : View.INVISIBLE);
    }

    /*
    Submits an entry.
    If the submission was successful, returns the created entry's ID. If not, returns null.
     */
    private Integer submitEntry(){

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String date = ((EditText)findViewById(R.id.editText_date)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        SegmentedControl entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        int selected = entryTypeControl.getLastSelectedAbsolutePosition();
        boolean purchase = selected == EXPENSE;
        UIEntryBuilder entryBuilder = UIEntryBuilderFactory.createUIEntryBuilder();
        try {
            entryBuilder.buildBaseEntry(amount, details, date, purchase);
            if(category != null) {
                entryBuilder.addCategory(category.getID());
            }
            if(purchase) {
                entryBuilder.addFlag(this.badSwitch.isChecked());
            }

            if(recurringSwitch.isChecked()){
                int[] recVals = getRecurrenceValues();
                entryBuilder.addRecurrencePeriod(recVals[DAYS],recVals[WEEKS],recVals[MONTHS],recVals[YEARS]);
            }

            return entryBuilder.getEntryID();
        }
        catch(UserInputException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast.makeText(this, "Invalid entry: "+userErrorMessage, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private int[] getRecurrenceValues(){
        String[] recValStrings = new String[DAYS+1];
        int[] recVals = new int[DAYS+1];
        recValStrings[YEARS] = ((EditText)findViewById(R.id.recurring_years)).getText().toString();
        recValStrings[MONTHS] = ((EditText)findViewById(R.id.recurring_months)).getText().toString();
        recValStrings[WEEKS] = ((EditText)findViewById(R.id.recurring_weeks)).getText().toString();
        recValStrings[DAYS] = ((EditText)findViewById(R.id.recurring_days)).getText().toString();

        for(int i = YEARS; i <= DAYS; i++){
            try{
                recVals[i] = Integer.parseInt(recValStrings[i]);
            }
            catch(NumberFormatException nfe){
                recVals[i] = 0;
            }
        }
        return recVals;
    }

}
