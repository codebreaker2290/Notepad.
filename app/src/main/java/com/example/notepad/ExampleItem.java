package com.example.notepad;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ExampleItem implements Serializable {
    private static ArrayList<ExampleItem> mExampleList;
    private String mText1;
    private String mText2;

    public ExampleItem(String text1,String text2){
        mText1 = text1;
        mText2 = text2;
     }



    public String getText1(){
        return mText1;
        }

     public String getText2() {

         return mText2;
     }
}