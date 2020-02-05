package com.codemonkeys9.budgeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codemonkeys9.budgeit.LogicLayer.LogicLayer;

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

                Intent i = new Intent(NewEntryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void submitEntry(){
        LogicLayer ll = LogicLayerHolder.getLogicLayer();

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

            ll.createEntry(amount,details,date);
        } catch(Exception e){
            //System.out.println(e.getClass()+" bad date format");
        }


        //System.out.println("amount: "+entry.getAmount()+" date: "+entry.getDate()+" details: "+entry.getDetails());
    }
}
