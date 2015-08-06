package com.kogitune.prelollipoptransition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kogitune.activity_transition.activity.ActivityTransitionLauncher;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class MainActivity extends AppCompatActivity {


    private Bitmap bitmap;
    private ImageView imageView;
    private String refer = "http://dubstep-light.info/wp-content/uploads/2014/09/bg_2VmkGk890fKEOBCj.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Picasso.with(this).load(refer).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                MainActivity.this.bitmap = bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, SubActivity.class);
                ActivityTransitionLauncher.with(MainActivity.this).from(v).image(bitmap).launch(intent);
            }
        });
        Picasso.with(this).load(refer).into(imageView);
    }
}
