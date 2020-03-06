package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import java.util.List;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentSelectedListener;

public class NewEntryActivity extends AppCompatActivity {
    // See res/values/strings.xml => "entry_types"
    private final static int
        INCOME = 0,
        EXPENSE = 1;

    UICategoryFetcher categoryFetcher;

    SegmentedControl entryTypeControl;
    Switch badSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEntry();
                setResult(RESULT_OK);
                finish();
            }
        });

        CategoryList categoryList = categoryFetcher.fetchAllCategories();
        List<Category> listOfCategories = categoryList.getReverseChrono();
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner_category);
        //create a list of items for the spinner.
        String[] items = new String[categoryList.size()];
        for(int i = 0; i < categoryList.size(); i++){
            items[i] = listOfCategories.get(i).getName().getValue();
        }
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

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
    }

    void showBadSwitch(boolean show) {
        this.badSwitch.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void submitEntry(){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String date = ((EditText)findViewById(R.id.editText_date)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        SegmentedControl entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        int selected = entryTypeControl.getLastSelectedAbsolutePosition();
        boolean purchase = selected == EXPENSE;

        try {
            entryManager.createEntry(amount, details, date, purchase);
        } catch(UserInputException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0 );
            toast.makeText(this, "Invalid entry: "+userErrorMessage, toast.LENGTH_LONG).show();

        }

    }

}
