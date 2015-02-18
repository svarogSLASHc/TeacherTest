package com.cs.teachertest.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.cs.teachertest.R;


public class MainActivity extends Activity {

    private View.OnClickListener mBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        commitFragment(new SetupFragment(), false);
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.action_bar);
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                View bottom = findViewById(R.id.bottomLayout_FrameLayout_MainActivity);
                bottom.getLayoutParams().height = actionBarHeight;
            }
            actionBar.getCustomView()
                    .findViewById(R.id.backButton_LinearLayout_ActionBar)
                    .setOnClickListener(mBackClick);
        }
    }

    private void commitFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getFragmentManager().findFragmentById(R.id.container_FrameLayout_MainActivity) == null) {
            transaction.add(R.id.container_FrameLayout_MainActivity, fragment);
        } else {
            transaction.replace(R.id.container_FrameLayout_MainActivity, fragment);
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
