package com.codemonkeys9.budgeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.Entry.EntryFactory;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreator;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreatorFactory;

import java.util.Date;
import java.text.SimpleDateFormat;

public class NewEntryActivity extends AppCompatActivity {

    static int entryID;

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
        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String date = ((EditText)findViewById(R.id.editText_date)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();
        int a = Integer.parseInt(amount);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        try {
            d = formatter.parse(date);
        } catch(Exception e){
            System.out.println(e.getClass()+" bad date format");
        }
        Entry entry = new EntryFactory().createEntry(a, entryID, details, d);

        System.out.println("amount: "+entry.getAmount()+" date: "+entry.getDate()+" details: "+entry.getDetails());

    }
}
