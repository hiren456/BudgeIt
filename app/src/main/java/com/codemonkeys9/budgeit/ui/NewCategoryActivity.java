package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;

public class NewCategoryActivity extends AppCompatActivity {
    // See res/values/strings.xml => "entry_types"
    private final static int
            SAVINGS = 0,
            BUDGET = 1;

    SegmentedControl categoryTypeControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = submitCategory();
                if(id != null) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newly_created_category_id", (int)id);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });


        this.categoryTypeControl = findViewById(R.id.control_incomeOrExpense);
        this.categoryTypeControl.setSelectedSegment(0);
    }

    /*
    Submits a category.
    If the submission was successful, returns the created category's ID. If not, returns null.
     */
    public Integer submitCategory() {
        UICategoryCreator categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        String details = ((EditText)findViewById(R.id.editText_details)).getText().toString();

        SegmentedControl entryTypeControl = findViewById(R.id.control_incomeOrExpense);
        int selected = entryTypeControl.getLastSelectedAbsolutePosition();
        boolean budget = selected == BUDGET;

        try {
            if(budget) return categoryCreator.createBudgetCategory(amount, details);
            else       return categoryCreator.createSavingsCategory(amount, details);
        } catch(UserInputException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast.makeText(this, "Invalid category: "+userErrorMessage, Toast.LENGTH_LONG).show();
            return null;
        }
    }

}
