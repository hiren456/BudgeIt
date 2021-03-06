package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

public class ModifyEntryDescriptionActivity extends AppCompatActivity {
    int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_entry_description);
        this.entryId = getIntent().getIntExtra("entryId",0);

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

        String date = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        try {
            Details details = DetailsFactory.fromString(date);
            entryManager.changeName(entryId,details);
        }
        catch(UserInputException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast.makeText(this, "Invalid entry: "+userErrorMessage, Toast.LENGTH_LONG).show();
        }
        catch(EntryDoesNotExistException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast.makeText(this, "Invalid entry: "+userErrorMessage, Toast.LENGTH_LONG).show();
        }
    }

}
