package com.insta.livraison_app;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.Toast;

public class LivraisonActivity extends FragmentActivity implements LocationListener {
	
	public static JSONObject livraisonDatas;	
	private connexionOpen connexionNotif;
	private boolean flagConnectivity;
	public static boolean isConnectionEnabled;
	public static Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livraison);
		
		flagConnectivity = false;		
		connexionNotif = new connexionOpen();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(connexionNotif, filter);
        this.updateConnectivity();
		
        if (LivraisonActivity.isConnectionEnabled) {
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
        } else {
        	this.buildAlertMessageNoConnection();
        }
	}
	
	public void onBackPressed() {
		moveTaskToBack(true);
	}
	
	public void onResume(){
		super.onResume();
	}
	
	public void onStop(){
		super.onStop();
		this.unregisterReceiver(connexionNotif);
	}
	
	public static void updateLivraisonDb(JSONObject datas, Context context) throws JSONException {
		Toast.makeText(context.getApplicationContext(), "inserting in db", Toast.LENGTH_SHORT).show();
		
		JSONObject livraisons = datas.getJSONObject("livraisons");
		JSONArray lastDaysLivraisons = livraisons.getJSONArray("priority");
		JSONArray dailyLivraisons = livraisons.getJSONArray("day");

		
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(context.getApplicationContext());		
		ClientAppDataSource ClientDataSource = new ClientAppDataSource(context.getApplicationContext());
		ClientDataSource.open();
		LivraisonDataSource.open();
		
//		LivraisonDataSource.deleteAll();
		
		for (int i = 0; i < lastDaysLivraisons.length(); i++) {

			JSONObject livraison = lastDaysLivraisons.getJSONObject(i);
			
			int id = Integer.parseInt(livraison.getString("id"));

			if (LivraisonDataSource.isLivraisonInDb(id) == 0) {
				String adresse = livraison.getString("adresse");
				String numero = livraison.getString("numero");
				String postal = livraison.getString("code_postal");
				String ville = livraison.getString("ville");
				String latitude = livraison.getString("latitude");
				String longitude = livraison.getString("longitude");
				String date = livraison.getString("date");
				String distance = livraison.getString("distance");
				String duration = livraison.getString("duration");
				int statut = Integer.parseInt(livraison.getString("statut"));
				int client_id = Integer.parseInt(livraison.getString("client_id"));
				int livreur_id = Integer.parseInt(livraison.getString("livreur_id"));

				//insertion du client
				JSONObject detail = livraison.getJSONObject("detail");
				JSONObject client = detail.getJSONObject("client");
				String client_nom = client.getString("nom");
				String client_prenom = client.getString("prenom");
				String client_telephone= client.getString("telephone");
				String client_email= client.getString("email");
				
				ClientDataSource.insertClient(client_id, client_nom, client_prenom, client_email, client_telephone);		
				
				LivraisonDataSource.insertLivraison(id, adresse, numero, postal, ville, latitude, 
						longitude, date, duration, distance, statut, client_id, livreur_id);
				
			}			
		}
		
		for (int i = 0; i < dailyLivraisons.length(); i++) {

			JSONObject livraison = dailyLivraisons.getJSONObject(i);
					
			int id = Integer.parseInt(livraison.getString("id"));
			if (LivraisonDataSource.isLivraisonInDb(id) == 0) {
				String adresse = livraison.getString("adresse");
				String numero = livraison.getString("numero");
				String postal = livraison.getString("code_postal");
				String ville = livraison.getString("ville");
				String latitude = livraison.getString("latitude");
				String longitude = livraison.getString("longitude");
				String date = livraison.getString("date");
				String distance = livraison.getString("distance");
				String duration = livraison.getString("duration");
				int statut = Integer.parseInt(livraison.getString("statut"));
				int client_id = Integer.parseInt(livraison.getString("client_id"));
				int livreur_id = Integer.parseInt(livraison.getString("livreur_id"));

				//insertion du client
				JSONObject detail = livraison.getJSONObject("detail");
				JSONObject client = detail.getJSONObject("client");
				String client_nom = client.getString("nom");
				String client_prenom = client.getString("prenom");
				String client_telephone= client.getString("telephone");
				String client_email= client.getString("email");
				
				ClientDataSource.insertClient(client_id, client_nom, client_prenom, client_email, client_telephone);
				
				LivraisonDataSource.insertLivraison(id, adresse, numero, postal, ville, latitude, 
						longitude, date, duration, distance, statut, client_id, livreur_id);
				
			}
		}		
		ClientDataSource.close();
		LivraisonDataSource.close();

		Toast.makeText(context.getApplicationContext(), "inserting in db", Toast.LENGTH_SHORT).show();
		
	}

	public class connexionOpen extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			Toast.makeText(context.getApplicationContext(), "broadcast receiver" + intent.getAction(), Toast.LENGTH_LONG).show();
			
			if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
				ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo wifiNetInfo =   connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
				if(wifiNetInfo.isConnected() || mobNetInfo.isConnected())
				{
					LivraisonActivity.isConnectionEnabled = true;
					if (flagConnectivity) {
						JsonLoader getJsonConnection = new JsonLoader("livraison", findViewById(android.R.id.content), (CheckBox)findViewById(R.id.SaveData));
				    	try {
							getJsonConnection.execute("http://livraison-app.esy.es/?json=livraison").get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
				} else {
					LivraisonActivity.isConnectionEnabled = false;
				}
			}
			
			if (!flagConnectivity) {
				flagConnectivity = true;
			}
			
					
		}		
	}
	
	public void updateConnectivity() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetInfo =   connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		if(wifiNetInfo.isConnected() || mobNetInfo.isConnected()) {
			LivraisonActivity.isConnectionEnabled = true;
		} else {
			LivraisonActivity.isConnectionEnabled = false;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		LivraisonActivity.location = location;
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	private void buildAlertMessageNoConnection() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Votre connexion semble être désactivée, nous ne pouvons actualiser les données")
	           .setPositiveButton("D'accord", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                    dialog.dismiss();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
	
}
