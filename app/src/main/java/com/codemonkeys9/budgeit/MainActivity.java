package com.codemonkeys9.budgeit;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.LogicLayer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private EntryAdapter entryAdapter;
    private EntryTypeVisibility visibility = EntryTypeVisibility.Both;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        LogicLayerHolder.init();
        LogicLayer logic = LogicLayerHolder.getLogicLayer();
        List<Entry> entries = logic.fetchAllEntrys();

        // Add fake data if there's no data in the DB already
        if(entries.isEmpty()) {
            logic.createEntry("-60", "Half-Life: Alyx Pre-order", "1/12/2019");
            for(int year = 2018; year <= 2020; year++) {
                for(int month = 1; month <= 12; month++) {
                    // Gas every week-ish
                    for(int j = 0; j < 4; j++) {
                        int day = j * 7 + 1;
                        logic.createEntry("-50", "Gas", day + "/" + month + "/" + year);
                    }
                    // Paycheck every two weeks-ish
                    logic.createEntry("1000", "Paycheck", "1/" + month + "/" + year);
                    logic.createEntry("1000", "Paycheck", "15/" + month + "/" + year);
                }
                logic.createEntry(
                        "-120",
                        "Something with an extremely, exceptionally, extraordinarily, staggeringly, shockingly, positively supercalifragilisticexpialidociously long description",
                        "13/2/" + year
                );
            }
            entries = logic.fetchAllEntrys();
        }

        this.entryAdapter = new EntryAdapter(entries);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setAdapter(this.entryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void refreshTimeline() {
        LogicLayer logic = LogicLayerHolder.getLogicLayer();
        List<Entry> entries = logic.fetchAllEntrys();
        entryAdapter.updateEntries(entries);
    }

    private void openNewEntryActivity(){
        Intent i = new Intent(this, NewEntryActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        refreshTimeline();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_toggle_income) {
            visibility = visibility.toggleIncome();
        } else if (id == R.id.action_toggle_expenses) {
            visibility = visibility.toggleExpenses();
        }
        if(id == R.id.action_toggle_income || id == R.id.action_toggle_expenses) {
            refreshTimeline();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
