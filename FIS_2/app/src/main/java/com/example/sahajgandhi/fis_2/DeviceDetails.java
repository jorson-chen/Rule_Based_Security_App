package com.example.sahajgandhi.fis_2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceDetails extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_details_activity);

        DeviceInformation di = new DeviceInformation(getApplicationContext());

        ListView list = (ListView) findViewById (R.id.theList);
        // textView.setText(di.name());

        ArrayList<String> names = di.name();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);

    }
}