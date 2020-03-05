package com.codemonkeys9.budgeit.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public final class MainPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private CharSequence[] titles;
    public MainPagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments = new Fragment[] {
            new EntriesFragment(),
            new CategoriesFragment(),
        };
        titles = new CharSequence[] {
            "Entries",
            "Categories",
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public EntriesFragment getEntriesFragment() {
        return (EntriesFragment) fragments[0];
    }
}
