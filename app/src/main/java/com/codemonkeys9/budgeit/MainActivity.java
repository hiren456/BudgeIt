package com.codemonkeys9.budgeit;

import android.os.Bundle;

import java.util.Date;
import java.util.List;
import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.LogicLayer;
import com.codemonkeys9.budgeit.LogicLayer.LogicLayerFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LogicLayer logic = new LogicLayerFactory().createLogicLayer();

        // Fetch all entries from "the beginning of time" to right now
        List<Entry> entries = logic.fetchAllEntrys(new Date(0), new Date());

        // Add fake data if there's no data in the DB already
        if(entries.isEmpty()) {
            logic.createEntry("-60", "Half-Life: Alyx Pre-order", "1/12/2019");
            logic.createEntry("-20", "Gas", "15/01/2020");
            logic.createEntry("1000", "Paycheck", "31/01/2020");
            entries = logic.fetchAllEntrys(new Date(0), new Date());
        }

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setAdapter(new EntryAdapter(entries));
        recycler.setLayoutManager(new LinearLayoutManager(this));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
