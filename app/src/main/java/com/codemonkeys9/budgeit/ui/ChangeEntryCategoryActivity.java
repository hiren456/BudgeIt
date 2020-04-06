package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;

import java.util.List;

public class ChangeEntryCategoryActivity extends AppCompatActivity {
    Spinner dropdown;
    Category category;
    int entryId;

    UICategoryFetcher categoryFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_entry_category);
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

        CategoryList categoryList = categoryFetcher.fetchAllCategories();
        List<Category> listOfCategories = categoryList.getReverseChrono();
        dropdown = findViewById(R.id.spinner_category);
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, listOfCategories);
        dropdown.setAdapter(categoryAdapter);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (Category)dropdown.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void submitEntry(){
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();

        try {
            categorizer.categorizeEntry(entryId,category.getID());
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
