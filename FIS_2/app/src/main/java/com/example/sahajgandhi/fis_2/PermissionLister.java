package com.example.sahajgandhi.fis_2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionLister {
    private Context context;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    PermissionLister(Context abc){
        context = abc;

    }

    public List<String> getAppsAsList()
    {
        listDataHeader = new ArrayList<String>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> installedApplications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);

        for (int i = 0; i < installedApplications.size(); i++) {
            if (!((installedApplications.get(i).flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0)) {
                PackageInfo appInfo = installedPackages.get(i);
                String appName = appInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                listDataHeader.add(appName);
            }
        }
        return listDataHeader;

    }

    public HashMap<String, List<String>> getPermissionsListForAllApps() {
        listDataChild = new HashMap<String, List<String>>();
        List<String> tempPermissions = new ArrayList<String>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> installedApplications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        String dataHolderString = "";

        for (int i = 0; i < installedApplications.size(); i++) {
            if (!((installedApplications.get(i).flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0)) {
                tempPermissions = new ArrayList<String>();
                PackageInfo appInfo = installedPackages.get(i);
                String appName = appInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(installedApplications.get(i).packageName,
                            PackageManager.GET_PERMISSIONS);
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    if (requestedPermissions != null) {
                        for (String currentPermission : requestedPermissions) {
                            String permissionNew = getPermissionNameOnly(currentPermission);
                            tempPermissions.add(permissionNew);
                        }
                    }
                    else{
                        tempPermissions.add("App Requires No PermissionPage");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listDataChild.put(appName, tempPermissions);
            }
        }
        return listDataChild;
    }

    public String getPermissionNameOnly(String fullPermissionDetails){
        String parts[] = fullPermissionDetails.split("\\.");
        return parts[parts.length-1];
    }
}