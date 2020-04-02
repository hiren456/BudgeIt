package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifier;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifierFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

public class ModifyCategoryAmountActivity extends AppCompatActivity {
    int catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_entry_amount);
        this.catId = getIntent().getIntExtra("catId",0);

        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCategory();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void submitCategory(){
        UICategoryModifier modifier = UICategoryModifierFactory.createUICategoryModifier();

        String amount = ((EditText)findViewById(R.id.editText_amount)).getText().toString();
        try {
            Amount goal = AmountFactory.fromString(amount);
            modifier.changeGoal(catId,goal);
        }
        catch(UserInputException e){
            String userErrorMessage = e.getUserErrorMessage();
            Toast.makeText(this, "Invalid entry: "+userErrorMessage, Toast.LENGTH_LONG).show();
        }
    }

}
