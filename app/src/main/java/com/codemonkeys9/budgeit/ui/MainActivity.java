package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {
    static final int DATE_RANGE_REQUEST = 0;
    ViewPager mainPager;
    EntriesFragment entriesFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is necessary for LocalDate to work with
        // API < 23
        AndroidThreeTen.init(this);
        DatabaseHolder.init();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.budgeit_logo);
        setSupportActionBar(toolbar);

        Button newEntryButton = findViewById(R.id.newEntryButton);
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewEntryActivity();
            }
        });

        this.mainPager = findViewById(R.id.main_pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        this.entriesFrag = adapter.getEntriesFragment();
        mainPager.setAdapter(adapter);

    }

    private void openNewEntryActivity() {
        Intent i = new Intent(this, NewEntryActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == DATE_RANGE_REQUEST) {
            if(data.hasExtra("start_date") && data.hasExtra("end_date")) {
                Bundle extras = data.getExtras();
                entriesFrag.hasDateFilter = true;
                entriesFrag.startDate = extras.getString("start_date");
                entriesFrag.endDate = extras.getString("end_date");
            }
        }
    }
}

