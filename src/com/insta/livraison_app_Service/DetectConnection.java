package com.insta.livraison_app_Service;

import java.util.concurrent.ExecutionException;

import com.insta.livraison_app.JsonLoader;
import com.insta.livraison_app.R;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.widget.CheckBox;
import android.widget.Toast;

public class DetectConnection extends Service {
	private IBinder mBinder = new MonServiceBinder();
	private ReceiverConnection myReceiver;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		myReceiver = new ReceiverConnection();
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(myReceiver, filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    super.onStartCommand(intent, flags, startId);
	    return START_STICKY;
	}
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(myReceiver != null)
		{
			unregisterReceiver(myReceiver);
		}	
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class ReceiverConnection extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		    NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
		    if(wifiNetInfo.isConnected() || mobNetInfo.isConnected())
		    {
		    	//TODO appeler la m�thode pour mettre la bdd � jours
		    	Toast.makeText(context,"izi money, izi life AVANT ", Toast.LENGTH_LONG).show();
		    	Activity activity = (Activity) context;
		    	JsonLoader getJsonConnection = new JsonLoader("livraison", activity.findViewById(android.R.id.content), (CheckBox)activity.findViewById(R.id.SaveData));
		    	try {
					getJsonConnection.execute("http://livraison-app.esy.es/?json=livraison").get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
		    }
		}
		
	}
	
	public class MonServiceBinder extends Binder{
		DetectConnection getService(){
			return DetectConnection.this;
		}
	}
}
