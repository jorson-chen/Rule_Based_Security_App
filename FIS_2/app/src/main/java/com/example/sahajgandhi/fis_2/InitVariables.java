package com.example.sahajgandhi.fis_2;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class InitVariables {

    private Context context;
    ArrayList<String> details;

    InitVariables(Context abc) {
        context = abc;
        details = new ArrayList<String>();

    }

    public String getPermissionsAverage() {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> installedApplications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        ArrayList<String> appNames = new ArrayList<String>();
        ArrayList<Double> appScore = new ArrayList<Double>();
        for (int i = 0; i < installedApplications.size(); i++) {
            PackageInfo appInfo = installedPackages.get(i);
            String appName = appInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            appNames.add(appName);
            try {
                PackageInfo packageInfo = pm.getPackageInfo(installedApplications.get(i).packageName,
                        PackageManager.GET_PERMISSIONS);
                String[] requestedPermissions = packageInfo.requestedPermissions;
                double highRiskPermissionCount = 0;
                double mediumRiskPermissionCount = 0;
                double lowPermissionCount = 0;
                double finalScore = 0.0;
                if (requestedPermissions != null) {
                    for (String currentPermission : requestedPermissions) {
                        String currentPermissionNew = getPermissionNameOnly(currentPermission);
                        if (checkHighRiskPermissions(currentPermissionNew)) {
                            highRiskPermissionCount++;
                        } else if (checkMediumRiskPermissions(currentPermissionNew)) {
                            mediumRiskPermissionCount++;
                        } else {
                            lowPermissionCount++;
                        }
                        finalScore = calculateAppScore(highRiskPermissionCount,
                                mediumRiskPermissionCount, lowPermissionCount);
                        appScore.add(finalScore);
                    }
                } else {
                    appScore.add(0.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (calculateAverageScore(appNames, appScore) < 2.5) {
            return "Permission_Low";
        } else {
            return "Permission_High";
        }
    }

    public double calculateAppScore(double highCount, double medCount, double lowCount) {
        double totalConsidered = highCount + medCount + lowCount;
        double finalScore = (5 * (highCount / totalConsidered)) +
                (3 * (medCount / totalConsidered)) +
                (1 * (lowCount / totalConsidered));
        return finalScore;
    }

    public double calculateAverageScore(ArrayList<String> appNames, ArrayList<Double> score) {
        double sum = 0.0;
        double averageScore = 0.0;
        for (int i = 0; i < appNames.size(); i++) {
            sum += score.get(i);
        }
        averageScore = sum / appNames.size();
        // Log.e("Average", "" + averageScore);
        return averageScore;
    }

    public boolean checkHighRiskPermissions(String permissionUnderConsideration) {
        String permList[] = {"READ_CALENDAR", "WRITE_CALENDAR", "CAMERA", "READ_CONTACTS",
                "WRITE_CONTACTS", "GET_ACCOUNTS", "ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION",
                "RECORD_AUDIO", "READ_PHONE_STATE", "READ_PHONE_NUMBERS", "CALL_PHONE",
                "ANSWER_PHONE_CALLS", "READ_CALL_LOG", "WRITE_CALL_LOG", "ADD_VOICEMAIL",
                "USE_SIP", "PROCESS_OUTGOING_CALLS", "BODY_SENSORS", "SEND_SMS", "RECEIVE_SMS",
                "READ_SMS", "RECEIVE_WAP_PUSH", "RECEIVE_MMS", "READ_EXTERNAL_STORAGE",
                "WRITE_EXTERNAL_STORAGE"};
        return Arrays.asList(permList).contains(permissionUnderConsideration);
    }

    public boolean checkMediumRiskPermissions(String permissionUnderConsideration) {
        String permList[] = {"ACCESS_LOCATION_EXTRA_COMMANDS", "ACCESS_NETWORK_STATE",
                "ACCESS_NOTIFICATION_POLICY", "ACCESS_WIFI_STATE", "BLUETOOTH", "BLUETOOTH_ADMIN",
                "BROADCAST_STICKY", "CHANGE_NETWORK_STATE", "CHANGE_WIFI_MULTICAST_STATE",
                "CHANGE_WIFI_STATE", "DISABLE_KEYGUARD", "EXPAND_STATUS_BAR", "GET_PACKAGE_SIZE",
                "INSTALL_SHORTCUT", "INTERNET", "KILL_BACKGROUND_PROCESSES", "MANAGE_OWN_CALLS",
                "MODIFY_AUDIO_SETTINGS", "NFC", "READ_SYNC_SETTINGS", "READ_SYNC_STATS",
                "RECEIVE_BOOT_COMPLETED", "REORDER_TASKS", "REQUEST_COMPANION_RUN_IN_BACKGROUND",
                "REQUEST_COMPANION_USE_DATA_IN_BACKGROUND", "REQUEST_DELETE_PACKAGES",
                "REQUEST_IGNORE_BATTERY_OPTIMIZATIONS", "SET_ALARM", "SET_WALLPAPER",
                "SET_WALLPAPER_HINTS", "TRANSMIT_IR", "USE_FINGERPRINT", "VIBRATE", "WAKE_LOCK",
                "WRITE_SYNC_SETTINGS"};
        return Arrays.asList(permList).contains(permissionUnderConsideration);
    }

    // function not used
    public boolean checkLowRiskPermissions(String permissionUnderConsideration) {
        String permList[] = {"BIND_ACCESSIBILITY_SERVICE", "BIND_AUTOFILL_SERVICE",
                "BIND_CARRIER_SERVICES", "BIND_CHOOSER_TARGET_SERVICE",
                "BIND_CONDITION_PROVIDER_SERVICE", "BIND_DEVICE_ADMIN", "BIND_DREAM_SERVICE",
                "BIND_INCALL_SERVICE", "BIND_INPUT_METHOD", "BIND_MIDI_DEVICE_SERVICE",
                "BIND_NFC_SERVICE", "BIND_NOTIFICATION_LISTENER_SERVICE", "BIND_PRINT_SERVICE",
                "BIND_SCREENING_SERVICE", "BIND_TELECOM_CONNECTION_SERVICE", "BIND_TEXT_SERVICE",
                "BIND_TV_INPUT", "BIND_VISUAL_VOICEMAIL_SERVICE", "BIND_VOICE_INTERACTION",
                "BIND_VPN_SERVICE", "BIND_VR_LISTENER_SERVICE", "BIND_WALLPAPER", "CLEAR_APP_CACHE",
                "MANAGE_DOCUMENTS", "READ_VOICEMAIL", "REQUEST_INSTALL_PACKAGES",
                "SYSTEM_ALERT_WINDOW", "WRITE_SETTINGS", "WRITE_VOICEMAIL"};
        return Arrays.asList(permList).contains(permissionUnderConsideration);
    }

    public String getPermissionNameOnly(String fullPermissionDetails) {
        String parts[] = fullPermissionDetails.split("\\.");
        // Log.e("full string is",  fullPermissionDetails + "-" + parts[parts.length-1]);
        return parts[parts.length - 1];
    }

    public String areAppsSideloaded() {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int sideLoadedAppsCount = 0;
        for (ApplicationInfo applicationInfo : packages) {
            if ((applicationInfo.flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
                    | ApplicationInfo.FLAG_SYSTEM)) > 0) {
                System.out.print("Systems apps will be removed here");
            } else {
                String installer = context.getPackageManager().
                        getInstallerPackageName(applicationInfo.packageName);
                if (installer == null) {
                    // The app that runs this code will also be considered in this count
                    sideLoadedAppsCount++;
                    // Log.e("Appname", applicationInfo.packageName +"----"+installer);
                }
//                if (Objects.equals(installer, "com.google.android.feedback")){
//                    Log.e("Appname", applicationInfo.packageName +"----"+installer);
//                }
// com.google.android.feedback, com.android.vending
            }
        }
        if ((sideLoadedAppsCount - 1) > 0) {
            return "Unknown_sources_present";
        } else {
            return "Unknown_sources_absent";
        }
    }

    public String isWIFISecure() {
//        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
//        List<ScanResult> networkList = wifi.getScanResults();
//
//        //get current connected SSID for comparison to ScanResult
//        WifiInfo wi = wifi.getConnectionInfo();
//        String currentSSID = wi.getSSID();
////        Log.e("SSID-Current ---- ", currentSSID);
////        Log.e("Wifi info -----", wi.toString());
//        String s = "";
//        if (networkList != null) {
//            for (ScanResult network : networkList) {
//                currentSSID = currentSSID.replaceAll("\"", "");
//                //check if current connected SSID
//                if (currentSSID.equals(network.SSID)) {
//                    //get capabilities of current connection
//                    String capabilities = network.capabilities;
//                    Log.d(TAG, network.SSID + " capabilities : " + capabilities);
//
//                    if (capabilities.contains("WPA2")) {
//                        // s += "\n WiFi status: 1";
//                        return "Wifi_On";
//                    } else if (capabilities.contains("WPA")) {
//                        // s += "\n WiFi status: 1";
//                        return "Wifi_On";
//                    } else if (capabilities.contains("WEP")) {
//                        // s += "\n WiFi status: 0.5";
//                        return "Wifi_On";
//                    } else {
//                        // s += "\n WiFi status: 0";
//                        return "Wifi_Off";
//                    }
//                }
//            }
//        } else {
//            s += "\n WiFi status: 0";
//            return "Wifi_Off";
//        }
        String s = "";
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getActiveNetworkInfo();
            String wifi_on_off = mWifi.getDetailedState().toString();
            if (mWifi.isConnected()) {
                if(wifi_on_off.equals("CONNECTED")) {
                    s = "Wifi_On";
                }
            } else {
                if(!(wifi_on_off.equals("CONNECTED"))) {
                    s = "Wifi_Off";
                }
            }
        }
        catch(Exception exp){
            s = "WiFi_Off";
        }

//
//        Log.e("Value = ", s);
        return s;
    }

    public String deviceEntrySecurity(Context context) {
        KeyguardManager km = context.getSystemService(KeyguardManager.class);
        String lock_status;
        if (km.isDeviceSecure()) {
            lock_status = "Lock";
        } else {
            lock_status = "Lock_Off";
        }
        return lock_status;
    }


    public String getAndroidVersion() {
        //String release = Build.VERSION.RELEASE;
        int LATESTVERSION = 28;
        int sdkVersion = Build.VERSION.SDK_INT;
        int differenceInVersions = LATESTVERSION - sdkVersion;
        if (sdkVersion > 25) {
            return "OS_Same";
        } else {
            return "OS_Diff";
        }
    }

    public String phoneRooted() {
        String buildTags = Build.TAGS;

        if (buildTags != null && buildTags.contains("test-keys")) {
            return "Root_On";
        } else {
            return "Root_Off";
        }
    }

    public String isAirplaneModeOn() {
        Boolean airplane = Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        if (airplane) {
            return "Airplane";
        } else {
            return "Airplane_Off";
        }
    }

    public String isDeviceEncrypted() {
        DevicePolicyManager dpm = context.getSystemService(DevicePolicyManager.class);
        try {
            int value = dpm.getStorageEncryptionStatus();
            String value_string;
            if (value == 0) {
                // value_string = "Not supported";
                return "Encryption_Off";
            } else if (value == 1) {
                // value_string = "Not active";
                return "Encryption_Off";
            } else if (value == 2) {
                // value_string = "Being activated";
                return "Encryption_Off";
            } else if (value == 4) {
                // value_string = "Active, but encryption key to be set";
                return "Encryption";
            } else {
                // value_string = "Active";
                return "Encryption";
            }

        } catch (Exception exp) {
            Log.e("Error Details", "");
            exp.printStackTrace();
        }
        return "Encryption_Off";
    }

    public String getAllDetails() {

        return getPermissionsAverage() + "\n" + areAppsSideloaded() + "\n" + isDeviceEncrypted()
                + "\n" + phoneRooted() + "\n" + getAndroidVersion() + "\n" + deviceEntrySecurity(context)
                + "\n" + isAirplaneModeOn() + "\n" + isWIFISecure();
    }

    public ArrayList<String> getAllDetailsList() {

        String temp = "";

//        temp = "NETWORK SECURITY STATUS";
//        details.add(temp);
        temp = "WiFi Status : " + isWIFISecure();
        details.add(temp);
        temp = "-----> WiFi should be off";
        details.add(temp);
        temp = "AirPlane Mode : " + isAirplaneModeOn();
        details.add(temp);
        temp = "-----> Airplane mode should be on";
        details.add(temp);
//        temp = "APP SECURITY STATUS";
//        details.add(temp);
        String str = getAndroidVersion();
        if(str.equals("OS_Diff")){
            str = "Not updated";
        }
        else{
            str = "Updated";
        }
        temp = "OS Status : " + str;
        details.add(temp);
        temp = "-----> OS should be updated";
        details.add(temp);
        temp = "Encryption Status : " + isDeviceEncrypted();
        details.add(temp);
        temp = "-----> Encryption should be present";
        details.add(temp);
        temp = "Root status: " + phoneRooted();
        details.add(temp);
//        temp = "DEVICE SECURITY STATUS";
//        details.add(temp);
        temp = "-----> Device should not be rooted";
        details.add(temp);
        temp = "Screen Lock Status : " + deviceEntrySecurity(context);
        details.add(temp);
        temp = "-----> Screen lock should be enabled";
        details.add(temp);
//        temp = "SOFTWARE SECURITY STATUS";
//        details.add(temp);
        temp = "Apk based Apps : " + areAppsSideloaded();
        details.add(temp);
        temp = "-----> Apps from known sources only";
        details.add(temp);
        temp = "HighRisk Permissions : " + getPermissionsAverage();
        details.add(temp);
        temp = "-----> Permission scores should be low";
        details.add(temp);
//        temp

        return details;

    }

}
