package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Description extends AppCompatActivity {
    public static final String BACKEXTRAHEAD="Header";
    public static final String BACKEXTRADES="Description";

    ArrayList<ExampleItem> exampleList;

    public void savetext(View v){

        Log.i("info","Button pressed!");

        saveData();

        loadData();

        //For giving changed data back to main activity

        EditText t = (EditText) findViewById(R.id.textView2);
        final String h = t.getText().toString();

        EditText e = (EditText) findViewById(R.id.editText2);
        final String d = e.getText().toString();

        Intent resultIntent= new Intent();
        resultIntent.putExtra(BACKEXTRAHEAD,h);
        resultIntent.putExtra(BACKEXTRADES,d);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private void saveData() {

        SharedPreferences sharedPreferences=getSharedPreferences("com.example.notepad", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson= new Gson();
        Log.i("button pressed","It worked");
        String json=gson.toJson(exampleList);
        editor.putString("task list",json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        exampleList = gson.fromJson(json, type);

        if (exampleList == null) {
            exampleList = new ArrayList<>();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        setTitle("Description");

        //Getting what was written in the edittexts of main activity

        Intent intent = getIntent();
        String heading = intent.getStringExtra(MainActivity.EXTRA_TEXTV);
        final String des = intent.getStringExtra(MainActivity.EXTRA_ETEXT);

        Bundle bundleObject=getIntent().getExtras();
        exampleList=(ArrayList<ExampleItem>)bundleObject.getSerializable("ArrayList");

        EditText textView1 = (EditText) findViewById(R.id.textView2);
        EditText editText = (EditText) findViewById(R.id.editText2);

        textView1.setText(heading);
        editText.setText(des);
    }
}