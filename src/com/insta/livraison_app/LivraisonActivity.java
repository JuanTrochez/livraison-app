package com.insta.livraison_app;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CheckBox;
import android.widget.Toast;

public class LivraisonActivity extends FragmentActivity {
	
	private connexionOpen connexionNotif;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livraison);
		
		if(connexionNotif == null)
		{
			connexionNotif = new connexionOpen();
			IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
	        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
	        this.registerReceiver(connexionNotif, filter);
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
	
	public void onResume(){
		super.onResume();

		if(connexionNotif == null)
		{
			connexionNotif = new connexionOpen();
			IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
	        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
	        this.registerReceiver(connexionNotif, filter);
		}		
	}
	
	public static void updateLivraisonDb(JSONObject datas, Context context) throws JSONException {
		Toast.makeText(context.getApplicationContext(), "inserting in db", Toast.LENGTH_LONG).show();
		
		JSONObject livraisons = datas.getJSONObject("livraisons");
		JSONArray lastDaysLivraisons = livraisons.getJSONArray("priority");
		JSONArray dailyLivraisons = livraisons.getJSONArray("day");

		
		LivraisonAppDataSource LivraisonDataSource = new LivraisonAppDataSource(context.getApplicationContext());		
		ClientAppDataSource ClientDataSource = new ClientAppDataSource(context.getApplicationContext());
		ClientDataSource.open();
		LivraisonDataSource.open();
		
		LivraisonDataSource.deleteAll();
		
		for (int i = 0; i < lastDaysLivraisons.length(); i++) {

			JSONObject livraison = lastDaysLivraisons.getJSONObject(i);
			
			int id = Integer.parseInt(livraison.getString("id"));

			Toast.makeText(context.getApplicationContext(), "Count if exist " + LivraisonDataSource.isLivraisonInDb(id), Toast.LENGTH_LONG).show();
			
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

				Toast.makeText(context.getApplicationContext(), "Livraison id " + id, Toast.LENGTH_LONG).show();			
				
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

				Toast.makeText(context.getApplicationContext(), "Livraison id " + id, Toast.LENGTH_LONG).show();			
				
				LivraisonDataSource.insertLivraison(id, adresse, numero, postal, ville, latitude, 
						longitude, date, duration, distance, statut, client_id, livreur_id);
				
			}
		}		
		ClientDataSource.close();
		LivraisonDataSource.close();
		
	}
	
	public class connexionOpen extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifiNetInfo =   connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			
			if(wifiNetInfo.isConnected() || mobNetInfo.isConnected())
			{
				JsonLoader getJsonConnection = new JsonLoader("livraison", findViewById(android.R.id.content), (CheckBox)findViewById(R.id.SaveData));
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
	
	
}
