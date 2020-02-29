package com.codemonkeys9.budgeit.ui;

import android.view.View;

// Thanks to https://stackoverflow.com/questions/46609356/recyclerview-on-item-selection-with-long-click

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}