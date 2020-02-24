package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.exceptions.UserInputException;

public class DateRangeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);

        final TextView startDate = findViewById(R.id.editText_start_date);
        final TextView endDate = findViewById(R.id.editText_end_date);


        Button submitButton = findViewById(R.id.button_submit);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();

                // creates two dummy dates so that the exception can be handled here rather than in the MainActivity.
                Date start = null;
                Date end = null;
                try{
                    start = DateFactory.fromString(startDate.getText().toString());
                    end = DateFactory.fromString(endDate.getText().toString());

                    DateInterval di = DateIntervalFactory.fromDate(start, end);
                }
                catch (UserInputException ie){
                    Toast.makeText(getApplicationContext(), "Error: "+ie.getUserErrorMessage(), Toast.LENGTH_LONG).show();
                    setResult(RESULT_CANCELED, data);
                    System.out.println(ie.getUserErrorMessage());
                    finish();
                }

                data.putExtra("start_date", startDate.getText().toString());
                data.putExtra("end_date", endDate.getText().toString());

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
