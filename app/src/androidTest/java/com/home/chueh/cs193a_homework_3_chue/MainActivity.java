package com.home.chueh.cs193a_homework_3_chue;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.*;

import cs193a.stanford.edu.hw3_madlibs.R;
import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {
    private Story myStory;
    static private String DEFAULT_STORY = "simple";
    private String selectedStory = DEFAULT_STORY;

    static private int FillInWords_TAG = 1234;
    static private String wordType_TAG = "typeofword";
    static private int ShowStory_TAG = 2345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.story_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStory = adapter.getItem(position).toString();
            //   Toast.makeText(this, String.format("Selected: %s",selectedStory), Toast.LENGTH_SHORT).show();
                Button button = (Button) findViewById(R.id.StartButton);
                button.setText(String.format("Start: %s",selectedStory));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void startGame(View view) {
        try {
            int resource_id = getResources().getIdentifier(selectedStory,"raw",getPackageName());

            myStory = new Story(new Scanner(getResources().openRawResource(resource_id)));
        }
        catch(Exception e){
            Toast.makeText(this, "Failed to open story", Toast.LENGTH_LONG).show();
        }

        if (myStory != null) {

           if (myStory.getPlaceholderRemainingCount() > 0) {
               Toast.makeText(this, String.format("words to fill: %d",myStory.getPlaceholderCount()), Toast.LENGTH_LONG).show();
               fillWords(myStory.getNextPlaceholder());
               Button button = (Button) findViewById(R.id.StartButton);
               button.setEnabled(false);
           }
            else{
               Toast.makeText(this, "Story has no placeholders", Toast.LENGTH_LONG).show();
           }

        }
    }

    private void fillWords(String wordType){
        Intent intent = new Intent(this, FillInWordsActivity.class);
        intent.putExtra(wordType_TAG, wordType);
        startActivityForResult(intent, FillInWords_TAG);
    }



    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FillInWords_TAG) {
            // came back from fill words activity

            String data = intent.getStringExtra("result");

            myStory.fillInPlaceholder(data);

            if (myStory.getPlaceholderRemainingCount() > 0) fillWords(myStory.getNextPlaceholder());
            else{

                Intent intent_showstory = new Intent(this, ShowStoryActivity.class);
                intent_showstory.putExtra("body",myStory.toString());
                startActivityForResult(intent_showstory, ShowStory_TAG);
            }
        }
        if (requestCode == ShowStory_TAG && resultCode == RESULT_OK){
            Button button = (Button) findViewById(R.id.StartButton);
            button.setEnabled(true);
        }

    }
}
