package com.insta.livraison_app;

import java.util.concurrent.ExecutionException;

import com.insta.livraison_app_Service.DetectConnection;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.Toast;

public class LivraisonActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livraison);
		
		Intent intent = new Intent(LivraisonActivity.this,DetectConnection.class);
		if(!isMyServiceRunning(DetectConnection.class))
		{
			startService(intent);
		}
		
		JsonLoader getJsonConnection = new JsonLoader("livraison", findViewById(android.R.id.content), (CheckBox)findViewById(R.id.SaveData));
		try {
			getJsonConnection.execute("http://livraison-app.esy.es/?json=livraison").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onBackPressed() {
		moveTaskToBack(true);
	}
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void onResume(){
		super.onResume();

		if(!isMyServiceRunning(DetectConnection.class))
		{
			Intent intent = new Intent(LivraisonActivity.this,DetectConnection.class);
			startService(intent);
		}		
	}
}
