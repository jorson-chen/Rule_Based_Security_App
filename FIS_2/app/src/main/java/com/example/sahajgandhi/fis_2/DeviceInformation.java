package com.example.sahajgandhi.fis_2;


import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;


public class DeviceInformation {
    ArrayList<String> details;
    private Context context;
    DeviceInformation(Context abc){
        details = new ArrayList<String>();
        context =  abc;
    }

    public ArrayList<String> name() {
        String s = "";
        String tempString = "";
//        s += "\n OS Version: " + System.getProperty("os.version") + " ("
//                + Build.VERSION.INCREMENTAL + ")";
        // s += " OS API Level : " + Build.VERSION.SDK_INT;
        // s += "\n Device : " + Build.DEVICE;
        // s += "\n Model (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")";

        tempString = "OS API Level: " + Build.VERSION.SDK_INT;
        addStringToList(tempString);
        tempString = "Device: " + Build.DEVICE;
        addStringToList(tempString);
        tempString = "Model (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")";
        addStringToList(tempString);

        // s += "\n Manufacturer: " + Build.MANUFACTURER;
        tempString = "Manufacturer: " + Build.MANUFACTURER;
        addStringToList(tempString);

        String sd_card = Environment.getExternalStorageState();
        if(sd_card.equals("mounted")) {
            // s += "\n SD Card state: Mounted";
            tempString = "SD Card state: Mounted";
            addStringToList(tempString);
        }
        else{
            //  s += "\n SD Card state: Not Mounted";
            tempString = "SD Card state: Not Mounted";
            addStringToList(tempString);
        }

//        String bluetooth = Settings.Global.BLUETOOTH_ON;
//        if(bluetooth.equals("bluetooth_on")) {
//            // s += "\n Bluetooth Status: On";
//            tempString = "Bluetooth Status: On";
//            addStringToList(tempString);
//        }
//        else{
//            // s += "\n Bluetooth Status: Off";
//            tempString = "Bluetooth Status: Off";
//            addStringToList(tempString);
//        }

        KeyguardManager km = context.getSystemService(KeyguardManager.class);
        try {
            boolean device_secure = km.isDeviceSecure();
            if(device_secure) {
                // s += "\n Phone lock enabled/disabled: Enabled";
                tempString = "Phone lock enabled/disabled: Enabled";
                addStringToList(tempString);
            }
            else{
                // s += "\n Phone lock enabled/disabled: Disabled";
                tempString = "Phone lock enabled/disabled: Disabled";
                addStringToList(tempString);
            }
        }
        catch (Exception exp){}

        String buildTags = Build.TAGS;

        if (buildTags != null && buildTags.contains("test-keys")) {
            // s += "\n Phone Rooted: Yes";
            tempString = "Phone Rooted: Yes";
            addStringToList(tempString);
        }
        else {
            // s += "\n Phone Rooted: No";
            tempString = "Phone Rooted: No";
            addStringToList(tempString);
        }

        DevicePolicyManager dpm = context.getSystemService(DevicePolicyManager.class);
        try {
            int value = dpm.getStorageEncryptionStatus();
            String value_string;
            if (value == 0){
                value_string = "Not supported";
            }
            else if (value == 1){
                value_string = "Not active";
            }
            else if (value == 2){
                value_string = "Being activated";
            }
            else if (value == 4){
                value_string = "Active, but encryption key to be set";
            }
            else{
                value_string = "Active";
            }

            // s += "\n Device encryption status: " + value_string;
            tempString = "Device encryption status: " + value_string;
            addStringToList(tempString);
        }
        catch (Exception exp){
            // s += "\n Device encryption status: Not supported";
            tempString = "Device encryption status: Not supported";
            addStringToList(tempString);
        }

        String dev_settings = Settings.Global.DEVELOPMENT_SETTINGS_ENABLED;
        if(dev_settings.equals("development_settings_enabled")){
            // s += "\n Dev Settings enabled or not: Enabled";
            tempString = "Dev Settings enabled or not: Enabled";
            addStringToList(tempString);
        }
        else{
            // s += "\n Dev Settings enabled or not: Disabled";
            tempString = "Dev Settings enabled or not: Disabled";
            addStringToList(tempString);
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        try {
            List<ScanResult> networkList = wifi.getScanResults();

            //get current connected SSID for comparison to ScanResult
            WifiInfo wi = wifi.getConnectionInfo();
            String currentSSID = wi.getSSID();

            if (networkList != null) {
                for (ScanResult network : networkList) {
                    //check if current connected SSID
                    if (currentSSID.equals(network.SSID)) {
                        //get capabilities of current connection
                        String capabilities = network.capabilities;
                        Log.d(TAG, network.SSID + " capabilities : " + capabilities);

                        if (capabilities.contains("WPA2")) {
                            s += "\n WiFi status: 1";
                        } else if (capabilities.contains("WPA")) {
                            s += "\n WiFi status: 1";
                        } else if (capabilities.contains("WEP")) {
                            s += "\n WiFi status: 0.5";
                        } else {
                            s += "\n WiFi status: 0";
                        }
                    }
                }
            }
        }
        catch (Exception exp){
            s += "\n WiFi status: 0";
        }

        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getActiveNetworkInfo();
            String wifi_on_off = mWifi.getDetailedState().toString();
            if (mWifi.isConnected()) {
//                s += "\n Wifi connected: yes";
                if(wifi_on_off.equals("CONNECTED")) {
                    // s += "\n Wifi: Connected";
                    tempString = "Wifi: Connected";
                    addStringToList(tempString);
                }
//                s += "\n 4: " + mWifi.toString();
            } else {
//                s += "\n Wifi connected: no";
                if(!(wifi_on_off.equals("CONNECTED"))) {
                    // s += "\n Wifi: Not Connected";
                    tempString= "Wifi: Not Connected";
                    addStringToList(tempString);
                }
            }
        }
        catch(Exception exp){
            // s += "\n Wifi : Not Connected";
            tempString= "Wifi: Not Connected";
            addStringToList(tempString);
        }

        Boolean airplane = Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        if (airplane) {
            // s += "\n Airplane Mode: On";
            tempString= "Airplane Mode: On";
            addStringToList(tempString);
        }
        else {
            // s += "\n Airplane Mode: Off";
            tempString= "Airplane Mode: Off";
            addStringToList(tempString);
        }


        return details;
    }

    public void addStringToList(String latestString){
        details.add(latestString);
    }
}
