package com.example.sahajgandhi.fis_2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ScoreEval extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.score_eval_activity);


        TextView textView = findViewById (R.id.score_eval);
        try {
            FileManipulation fm = new FileManipulation(getApplicationContext());
            fm.writeFile();
        }
        catch (Exception exp){}

        String value;
        int score = 0;
        Boolean checker;
        String algorithm = "forward";
        for (int i = 1; i < 5; i++){
            Entail et = new Entail(getApplicationContext(), algorithm, i, "sample.txt");
            checker = et.ExpertShell(algorithm, i);
            if(checker){
                score = i;
            }
        }

        score = score * 25;
        value = Integer.toString(score);
        value = value + "% Secure !";
        textView.setText(value);
        File yourFile = new File(Environment.getExternalStorageDirectory(), "sample.txt");
        if(yourFile.exists()){
            yourFile.delete();
        }

//        ListView text = findViewById(R.id.listBetter);
        InitVariables ini = new InitVariables(getApplicationContext());
        Log.e("Values ----- ", ini.getAllDetails());

        ListView list = (ListView) findViewById(R.id.listBetter);
        ArrayList<String> data = ini.getAllDetailsList();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

    }
}