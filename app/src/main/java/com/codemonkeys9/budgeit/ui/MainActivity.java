package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;

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
    CategoriesFragment categoriesFrag;

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

        mainPager = findViewById(R.id.main_pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        entriesFrag = adapter.getEntriesFragment();
        categoriesFrag = adapter.getCategoriesFragment();
        mainPager.setAdapter(adapter);

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

