package com.home.chueh.cs193a_homework_3_chue;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.Scanner;

import cs193a.stanford.edu.hw3_madlibs.R;
import stanford.androidlib.SimpleActivity;

public class FillInWordsActivity extends SimpleActivity {

    private String wordType;

    static private String wordType_TAG = "typeofword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_words);
        Intent intent = getIntent();
        try {
            wordType = intent.getStringExtra(wordType_TAG);
            startFillingWords();
        }catch(Exception e){
            Intent intent2 = new Intent();
            setResult(RESULT_OK, intent2);
            finish(); // calls onDestroy
        }
    }

    private void startFillingWords() {
        String placeholder = "";
        EditText edittext = (EditText)findViewById(R.id.userInput);
        edittext.setHint(wordType);
    }

    public void wordSubmitted(View view) {
        EditText edittext = (EditText)findViewById(R.id.userInput);
        String data = edittext.getText().toString();
        if (data.length()==0){
            Toast.makeText(this, "Please enter a word!", Toast.LENGTH_SHORT).show();
            edittext.setHint(wordType);
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("result", data);
            setResult(RESULT_OK, intent);
            finish(); // calls onDestroy
        }
    }
}
