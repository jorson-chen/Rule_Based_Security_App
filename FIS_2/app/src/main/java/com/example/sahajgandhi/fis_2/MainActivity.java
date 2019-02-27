package com.example.sahajgandhi.fis_2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.sahajgandhi.fis_2.PermissionHandler;
import com.example.sahajgandhi.fis_2.Permissions;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    Button button1;
    Button button2;
    Button button3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
//        requestStorage();

        // Locate the button in activity_main.xml
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // Capture button clicks
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, ScoreEval.class);
                startActivity(myIntent);
            }
        });

        // Capture button clicks
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, DeviceDetails.class);
                startActivity(myIntent);
            }
        });

        // Capture button clicks
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, PermissionPage.class);
                startActivity(myIntent);
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.activity_main,menu);
        return true;
    }

//    public void requestStorage(View view) {
//        Permissions.check(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                "Storage permissions are required to write system details into the storage to check security score", new Permissions.Options()
//                        .setRationaleDialogTitle("Info"),
//                new PermissionHandler() {
//                    @Override
//                    public void onGranted() {
//                        Toast.makeText(MainActivity.this, "Storage granted.", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//                        Toast.makeText(context, "torage Denied:\n" + Arrays.toString(deniedPermissions.toArray()),
//                                Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public boolean onBlocked(Context context, ArrayList<String> blockedList) {
//                        Toast.makeText(context, "Storage blocked:\n" + Arrays.toString(blockedList.toArray()),
//                                Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//
//                    @Override
//                    public void onJustBlocked(Context context, ArrayList<String> justBlockedList,
//                                              ArrayList<String> deniedPermissions) {
//                        Toast.makeText(context, "Storage just blocked:\n" + Arrays.toString(deniedPermissions.toArray()),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}