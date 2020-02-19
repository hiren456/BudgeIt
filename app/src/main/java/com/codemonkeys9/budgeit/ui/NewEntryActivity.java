package com.codemonkeys9.budgeit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

public class NewEntryActivity extends AppCompatActivity {


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
    }

    public void submitEntry(){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String date = ((EditText)findViewById(R.id.editText_date)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        // Hiren is working on creating exceptions
        // once that is done then createEntry will throw
        // an exception such as "InvalidParameterException"
        // this will happen if any of the above strings are invalid
        // I think it would be good to have a diff. exception for each type of invalid string
        // such as "InvalidDateFormat", "FutureDateException", "EmptyDetailsException",
        // "OldDateException", and "InvalidAmountFormat"
        // then you can check for each type and react/inform the user accordingly
        try {

            ToggleButton tb = findViewById(R.id.button_incomeOrExpense);

            if ( tb.isChecked() ) {
                entryManager.createEntry("-"+amount,details,date);
            }

            else {
                entryManager.createEntry(amount,details,date);
            }

        } catch(Exception e){
            //System.out.println(e.getClass()+" bad date format");
        }

    }
}
