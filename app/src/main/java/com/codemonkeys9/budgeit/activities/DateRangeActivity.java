package com.codemonkeys9.budgeit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;

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
                data.putExtra("start_date", startDate.getText().toString());
                data.putExtra("end_date", endDate.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
