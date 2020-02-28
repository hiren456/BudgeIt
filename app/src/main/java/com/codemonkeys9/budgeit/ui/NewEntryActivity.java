package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;

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
