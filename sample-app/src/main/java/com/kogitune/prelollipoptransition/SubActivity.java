package com.kogitune.prelollipoptransition;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kogitune.activity_transition.activity.ActivityTransition;
import com.kogitune.activity_transition.activity.ExitActivityTransition;
import com.kogitune.activity_transition.views.ExitViewTransition;
import com.kogitune.activity_transition.views.ViewTransition;


public class SubActivity extends ActionBarActivity {

    private ExitActivityTransition exitTransition;
    private ExitViewTransition viewExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        exitTransition = ActivityTransition
                .with(getIntent())
                .duration(10000)
                .to(findViewById(R.id.sub_imageView))
                .start(savedInstanceState);

        viewExit = ViewTransition
                .with(findViewById(R.id.from_inner_image), findViewById(R.id.to_inner_image))
                .withDuration(7000)
                .create()
                .start();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
        viewExit.exit();
    }
}
