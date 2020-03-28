package com.example.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import static com.example.notepad.Description.BACKEXTRADES;
import static com.example.notepad.Description.BACKEXTRAHEAD;

public class MainActivity<i> extends AppCompatActivity {

    private  EditText textview;
    private EditText editText;

    private RecyclerView mRecyclerView;
    private examplAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String EXTRA_TEXTV = "Header";
    public static final String EXTRA_ETEXT = "Description";

    private ArrayList<ExampleItem> exampleList ;

    int i=0;

    //Starting new intent
    public void openDescription(View v) {

        textview =findViewById(R.id.textView);
        String header = textview.getText().toString();

        editText =findViewById(R.id.editText);
        String descrip = editText.getText().toString();

    //Giving data from the first activity to the other
        Intent intent = new Intent(this, Description.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("ArrayList",exampleList);
        intent.putExtra(EXTRA_TEXTV, header);
        intent.putExtra(EXTRA_ETEXT, descrip);
        intent.putExtra("list",bundle);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        EditText hedit = findViewById(R.id.textView);
        EditText dedit = findViewById(R.id.editText);
        FloatingActionButton fab= findViewById(R.id.fab);


        setSupportActionBar(toolbar);

        //For creating new element in the first activity

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i=exampleList.size();
                exampleList.add(i,new ExampleItem("",""));
                mAdapter.notifyItemInserted(i);
            }
        });
    }

    //RECEIVING INFORMATION FROM THE OTHER LAYOUT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String he=data.getStringExtra(BACKEXTRAHEAD);
                String de=data.getStringExtra(BACKEXTRADES);
                textview.setText(he);
                editText.setText(de);
                Log.i("wow","it worked till here");

            }
        }

    }

    public void createExampleList(){
      //*
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        exampleList = gson.fromJson(json, type);

        if (exampleList == null) {
            exampleList = new ArrayList<>();
        }
        exampleList.add(new ExampleItem("", ""));
    }

    public void buildRecyclerView(){

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new examplAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new examplAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void removeItem(int position) {
        exampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}




