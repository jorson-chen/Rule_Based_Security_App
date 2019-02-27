package com.example.sahajgandhi.fis_2;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FileManipulation {

    private Context context;

    FileManipulation(Context abc) throws Exception{
        context =  abc;
    }

    public static BufferedReader br;
    public static String currentLine;
    public static String sentencesS = "";


    public String readFiles() {
        String fileName = "kb.txt";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            while ((currentLine = br.readLine()) != null) {
                sentencesS += currentLine + "\n";

            }
        } catch (Exception exp) {
            Log.e("Value ----- ", exp.toString());
        }
        Log.e("sentencesS ----- ", sentencesS);
        return sentencesS;
    }


    public boolean isExternalStorageWritable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.e("State", "Yes, Writable");
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isExternalStorageReadable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            Log.e("State", "Yes, Readable");
            return true;
        }
        else {
            return false;
        }
    }


    private static final String FILE_NAME = "sample.txt";

    public void writeFile(){
        if(isExternalStorageWritable()){
            InitVariables init = new InitVariables(context);

            String text = readFiles() + init.getAllDetails();
            Log.d("Rules ----", text);
            File textFile = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

            try {
                FileOutputStream fos = new FileOutputStream(textFile);
                fos.write(text.getBytes());
                fos.close();

            }
            catch (IOException e) {
                Log.e(e.toString(), "");
            }
        }
        else {
            Log.e("Cannot External Storage", "");
        }
    }


    public void readFile(){
        if(isExternalStorageWritable()){
            StringBuilder sb = new StringBuilder();
            try {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
                // FileInputStream fis = new FileInputStream(textFile);

                //if(fis!=null) {
                //InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(textFile)));

                String line = null;

                while ((line = buff.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //fis.close();
                //}
                Log.e("sb", sb.toString());
            }
            catch (IOException e){
                Log.e(e.toString(), "");
            }
        }
        else {
            Log.e("Cannot Read Storage", "");
        }
    }
}

