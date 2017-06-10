package com.example.f1access;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;


public class F1Service extends AccessibilityService {
	
		

@Override
protected void onServiceConnected() {
	
}

@Override
public void onAccessibilityEvent(AccessibilityEvent aEvent) {
	Log.d("Package_name", aEvent.getPackageName().toString());
		
	
}

@Override 
public void onInterrupt() {
	
}

@Override
protected boolean onKeyEvent(KeyEvent event) {
	NotificationCompat.Builder nb = new NotificationCompat.Builder(this);			
nb.setSmallIcon(R.drawable.ic_launcher);
nb.setContentTitle("Key Press");
nb.setContentText("Key is Pressed " + event.getMetaState());

		NotificationManager mn = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
mn.notify(11, nb.build());
Log.d("Nits Key code", Integer.toString(event.getKeyCode()));
boolean process = isProcessRunning();
	Log.d("Process result", Boolean.toString(process));

if(event.getKeyCode(	) == 131) {
	Intent i = new Intent();
	i.setComponent(new ComponentName("com.winwithjoy", "com.winwithjoy.MainActivity"));
	startActivity(i);	

		Log.d("If", "Inside if");
		return true;	
	}
int keyCode;
keyCode = event.getKeyCode();
if (keyCode== 117 || keyCode==118 || keyCode==71 || keyCode==72 || keyCode==120) {
	return true;
}	

	return false;
	}

@Override
public boolean onUnbind(Intent intent) {
	
	return false;
}

boolean isProcessRunning(String pName) {
	if (pName == null) {
		return false;
	}
	else {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List <RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
		for (RunningAppProcessInfo p : processes) {
			Log.d("Process_name", p.processName);
			if (pName.equals(p.processName)) {
				return true;
			}
		}
			}
	
	return false;
}

boolean isProcessRunning() {
		UsageStatsManager uStats = (UsageStatsManager) getSystemService("usagestats");
		List<UsageStats> lUsageStats=null;
		long time = System.currentTimeMillis();
		long lTime=0;
	if(hasPermission()){
			lUsageStats = uStats.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time-1000*10, time);
	}
String pName="";
		for(UsageStats lStats:lUsageStats) {
					if(lTime==0) {
			lTime=lStats.getLastTimeUsed();
			pName=lStats.getPackageName();						
}
		else if(lTime>lStats.getLastTimeUsed()) {
			pName=lStats.getPackageName();
		}
		lTime=lStats.getLastTimeUsed();
	}
		if (pName.equals("com.winwithjoy")) {
			Log.d("MyPKG", pName);
			return true;
		}
		Log.d("MyPKG", pName);
	return false;
	}

private boolean hasPermission() {
    AppOpsManager appOps = (AppOpsManager)
            getSystemService(Context.APP_OPS_SERVICE);
    int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(), getPackageName());
    return mode == AppOpsManager.MODE_ALLOWED;
//    return ContextCompat.checkSelfPermission(this,
//            Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED;
}


}
