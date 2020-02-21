package com.codemonkeys9.budgeit.ui;

import androidx.appcompat.app.AppCompatActivity;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codemonkeys9.budgeit.logiclayer.LogicLayer;
import com.codemonkeys9.budgeit.R;

public class NewEntryActivity extends AppCompatActivity {
    // See res/values/strings.xml => "entry_types"
    private final static int
        INCOME = 0,
        EXPENSE = 1;

    SegmentedControl entryTypeControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        this.entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        this.entryTypeControl.setSelectedSegment(0);
    }

    public void submitEntry(){
        LogicLayer ll = LogicLayerHolder.getLogicLayer();

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String date = ((EditText)findViewById(R.id.editText_date)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        SegmentedControl entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        int selected = entryTypeControl.getLastSelectedAbsolutePosition();
        if(selected == EXPENSE) {
            amount = "-" + amount;
        }

        // Hiren is working on creating exceptions
        // once that is done then createEntry will throw
        // an exception such as "InvalidParameterException"
        // this will happen if any of the above strings are invalid
        // I think it would be good to have a diff. exception for each type of invalid string
        // such as "InvalidDateFormat", "FutureDateException", "EmptyDetailsException",
        // "OldDateException", and "InvalidAmountFormat"
        // then you can check for each type and react/inform the user accordingly
        try {
            ll.createEntry(amount, details, date);
        } catch(Exception e){
            //System.out.println(e.getClass()+" bad date format");
        }

    }
}
