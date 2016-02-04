package com.home.chueh.cs193a_homework_3_chue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.widget.*;

import cs193a.stanford.edu.hw3_madlibs.R;
import stanford.androidlib.SimpleActivity;

public class ShowStoryActivity extends SimpleActivity {
    // TODO: finish me!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_story);

        Intent intent = getIntent();
        String body = intent.getStringExtra("body");
        TextView textBody = (TextView) findViewById(R.id.textBody);
        textBody.setText(Html.fromHtml(body));
    }

    public void backToHome(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish(); // calls onDestroy
    }

}
