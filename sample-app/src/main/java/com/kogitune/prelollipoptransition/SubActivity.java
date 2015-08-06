package com.kogitune.prelollipoptransition;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kogitune.activity_transition.activity.ActivityTransition;
import com.kogitune.activity_transition.activity.ExitActivityTransition;


public class SubActivity extends ActionBarActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        exitTransition = ActivityTransition
                .with(getIntent())
                .duration(500)
                .to(findViewById(R.id.sub_imageView))
                .start(savedInstanceState);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);

    }
}
