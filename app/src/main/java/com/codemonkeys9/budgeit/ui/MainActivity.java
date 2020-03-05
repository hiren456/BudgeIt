package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity implements EntriesFragment.OnFragmentInteractionListener {
    static final int DATE_RANGE_REQUEST = 0;
    EntriesFragment entriesFrag;

    private boolean showCategories = false;
    private MenuItem categoryViewToggle;
    private MenuItem allViewToggle;

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

        this.entriesFrag = (EntriesFragment) getSupportFragmentManager()
            .findFragmentById(R.id.fragment_entries);
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
                invalidateOptionsMenu();
                entriesFrag.startDate = extras.getString("start_date");
                entriesFrag.endDate = extras.getString("end_date");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        categoryViewToggle = menu.findItem(R.id.action_set_category_view);
        allViewToggle = menu.findItem(R.id.action_set_all_view);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(showCategories) {
            categoryViewToggle.setVisible(false);
            allViewToggle.setVisible(true);
        } else {
            categoryViewToggle.setVisible(true);
            allViewToggle.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_set_category_view){
            Toast.makeText(this, "Toggled category view. ", Toast.LENGTH_SHORT).show();
            categoryViewToggle.setVisible(false);
            allViewToggle.setVisible(true);
            return true;
        } else if (id == R.id.action_set_all_view){
            Toast.makeText(this, "Toggled all view. ", Toast.LENGTH_SHORT).show();
            categoryViewToggle.setVisible(true);
            allViewToggle.setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

